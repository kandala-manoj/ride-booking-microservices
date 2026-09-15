package com.alpha.locationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.locationservice.Dto.FareEstimateRequestDto;
import com.alpha.locationservice.Dto.FareEstimateResponseDto;
import com.alpha.locationservice.Dto.LocationResDto;
import com.alpha.locationservice.Dto.ResponseStructure;
import com.alpha.locationservice.Dto.RiderLocationRequest;
import com.alpha.locationservice.Dto.VehicleFareDto;
import com.alpha.locationservice.exception.InvalidSearchKeyException;
import com.alpha.locationservice.exception.LocationNotFoundException;

@Service
public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_GEO_KEY =
            "RIDERCURRENTLOCATION";

    private static final double DEFAULT_RADIUS_KM =
            3.0;

    // =====================================================
    // FIND DESTINATION
    // =====================================================

    public ResponseStructure<List<LocationResDto>>
    findDestinationLocation(String skey) {

        if (skey == null || skey.trim().length() < 3) {
            throw new InvalidSearchKeyException();
        }

        String url =
                "https://api.locationiq.com/v1/autocomplete"
                + "?key=pk.c523b96ee128ce92d47105be43236ffd"
                + "&q=" + skey
                + "&limit=5"
                + "&dedupe=1";

        ArrayList<Map<String, Object>> response =
                restTemplate.getForObject(
                        url,
                        ArrayList.class
                );

        if (response == null || response.isEmpty()) {
            throw new LocationNotFoundException();
        }

        List<LocationResDto> locations =
                new ArrayList<>();

        for (Map<String, Object> object : response) {

            Map<String, Object> address =
                    (Map<String, Object>) object.get("address");

            LocationResDto dto =
                    new LocationResDto();

            String name;

            if (object.get("name") != null) {

                name = object.get("name").toString();

            } else {

                name = object.get("display_name")
                        .toString()
                        .split(",")[0];
            }

            dto.setName(name);

            dto.setDisplayName(
                    object.get("display_name") != null
                            ? object.get("display_name").toString()
                            : null
            );

            dto.setLatitude(
                    Double.parseDouble(
                            object.get("lat").toString()
                    )
            );

            dto.setLongitude(
                    Double.parseDouble(
                            object.get("lon").toString()
                    )
            );

            if (address != null) {

                dto.setCity(
                        address.get("city") != null
                                ? address.get("city").toString()
                                : null
                );

                dto.setState(
                        address.get("state") != null
                                ? address.get("state").toString()
                                : null
                );

                dto.setCountry(
                        address.get("country") != null
                                ? address.get("country").toString()
                                : null
                );

                dto.setPincode(
                        address.get("postcode") != null
                                ? address.get("postcode").toString()
                                : null
                );
            }

            locations.add(dto);
        }

        ResponseStructure<List<LocationResDto>> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Locations found successfully."
        );

        rs.setData(locations);

        return rs;
    }

    // =====================================================
    // FARE ESTIMATION
    // =====================================================

    public ResponseStructure<FareEstimateResponseDto>
    estimateFare(FareEstimateRequestDto dto) {

        String url =
                "https://us1.locationiq.com/v1/directions/driving/"
                + dto.getPickupLongitude()
                + ","
                + dto.getPickupLatitude()
                + ";"
                + dto.getDropLongitude()
                + ","
                + dto.getDropLatitude()
                + "?key=pk.c5c45a2225a49311e465d97f4ba195c5"
                + "&overview=false";

        Map<String, Object> result =
                restTemplate.getForObject(
                        url,
                        Map.class
                );

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>)
                        result.get("routes");

        Map<String, Object> route =
                routes.get(0);

        double distance =
                ((Number) route.get("distance"))
                        .doubleValue() / 1000;

        int estimatedTime =
                ((Number) route.get("duration"))
                        .intValue() / 60;

        List<VehicleFareDto> vehicles =
                new ArrayList<>();

        // =================================================
        // BIKE
        // =================================================

        VehicleFareDto bike =
                new VehicleFareDto();

        bike.setVehicleType("BIKE");

        if (distance <= 5) {

            bike.setBaseFare(25);
            bike.setPerKmCharge(7);

        } else {

            bike.setBaseFare(30);
            bike.setPerKmCharge(8);
        }

        bike.setEstimatedFare(
                bike.getBaseFare()
                + (distance * bike.getPerKmCharge())
        );

        vehicles.add(bike);

        // =================================================
        // AUTO
        // =================================================

        VehicleFareDto auto =
                new VehicleFareDto();

        auto.setVehicleType("AUTO");

        if (distance <= 5) {

            auto.setBaseFare(40);
            auto.setPerKmCharge(12);

        } else {

            auto.setBaseFare(50);
            auto.setPerKmCharge(13);
        }

        auto.setEstimatedFare(
                auto.getBaseFare()
                + (distance * auto.getPerKmCharge())
        );

        vehicles.add(auto);

        // =================================================
        // CAB
        // =================================================

        VehicleFareDto cab =
                new VehicleFareDto();

        cab.setVehicleType("CAB");

        if (distance <= 5) {

            cab.setBaseFare(80);
            cab.setPerKmCharge(18);

        } else {

            cab.setBaseFare(100);
            cab.setPerKmCharge(20);
        }

        cab.setEstimatedFare(
                cab.getBaseFare()
                + (distance * cab.getPerKmCharge())
        );

        vehicles.add(cab);

        // =================================================
        // RESPONSE
        // =================================================

        FareEstimateResponseDto response =
                new FareEstimateResponseDto();

        response.setPickupAddress(
                dto.getPickupAddress()
        );

        response.setDropAddress(
                dto.getDropAddress()
        );

        response.setDistance(distance);

        response.setEstimatedTime(
                estimatedTime
        );

        response.setVehicleOptions(
                vehicles
        );

        ResponseStructure<FareEstimateResponseDto> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Fare estimated successfully."
        );

        rs.setData(response);

        return rs;
    }

    // =====================================================
    // UPDATE RIDER LOCATION
    // =====================================================

    public void updateLocation(
            RiderLocationRequest request) {

        String vehicleType =
                request.getVehicleType()
                        .toUpperCase();

        String key =
                request.getRider()
                + "_"
                + vehicleType;

        Point point =
                new Point(
                        request.getLongitude(),
                        request.getCurrentLatitude()
                );

        redisTemplate.opsForGeo()
                .add(
                        REDIS_GEO_KEY,
                        point,
                        key
                );

        System.out.println(
                "Rider location stored: " + key
        );
    }

    // =====================================================
    // FIND NEARBY RIDERS
    // DEFAULT RADIUS = 3 KM
    // =====================================================

    public List<String> findNearbyRiders(
            double latitude,
            double longitude,
            String vehicleType) {

        Circle circle =
                new Circle(
                        new Point(
                                longitude,
                                latitude
                        ),
                        new Distance(
                                DEFAULT_RADIUS_KM,
                                Metrics.KILOMETERS
                        )
                );

        GeoResults<
                RedisGeoCommands.GeoLocation<Object>
        > results =
                redisTemplate.opsForGeo()
                        .radius(
                                REDIS_GEO_KEY,
                                circle
                        );

        List<String> riders =
                new ArrayList<>();

        System.out.println(
                "--------------------------------"
        );

        System.out.println(
                "Pickup Latitude  : "
                + latitude
        );

        System.out.println(
                "Pickup Longitude : "
                + longitude
        );

        System.out.println(
                "Search Radius    : "
                + DEFAULT_RADIUS_KM
                + " KM"
        );

        System.out.println(
                "Vehicle Type     : "
                + vehicleType
        );

        System.out.println(
                "--------------------------------"
        );

        if (results != null) {

            for (
                    GeoResult<
                            RedisGeoCommands.GeoLocation<Object>
                    > result
                    : results.getContent()
            ) {

                String riderKey =
                        result.getContent()
                                .getName()
                                .toString();

                System.out.println(
                        "Rider found in GEO search: "
                        + riderKey
                );

                if (
                        riderKey.endsWith(
                                "_"
                                + vehicleType.toUpperCase()
                        )
                ) {

                    riders.add(riderKey);
                }
            }
        }

        System.out.println(
                "--------------------------------"
        );

        System.out.println(
                "Nearby "
                + vehicleType
                + " riders: "
                + riders
        );

        System.out.println(
                "--------------------------------"
        );

        return riders;
    }
}