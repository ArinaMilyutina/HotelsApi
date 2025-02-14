package com.example.hotelsapi.specifications;

import com.example.hotelsapi.dto.hotel.HotelSearchCriteria;
import com.example.hotelsapi.entity.Amenity;
import com.example.hotelsapi.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class HotelSpecifications {
    private static final String NAME = "name";
    private static final String BRAND = "brand";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String COUNTY = "county";
    private static final String AMENITIES = "amenities";


    public static Specification<Hotel> getHotelsByCriteria(HotelSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), "%" + criteria.getName().toLowerCase() + "%"));
            }
            if (criteria.getBrand() != null && !criteria.getBrand().isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(BRAND)), criteria.getBrand().toLowerCase()));
            }
            if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(ADDRESS).get(CITY)), criteria.getCity().toLowerCase()));
            }

            if (criteria.getCounty() != null && !criteria.getCounty().isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(ADDRESS).get(COUNTY)), criteria.getCounty().toLowerCase()));
            }

            if (criteria.getAmenities() != null && !criteria.getAmenities().isEmpty()) {
                for (String amenityStr : criteria.getAmenities()) {
                    Amenity amenity = findAmenityByString(amenityStr);
                    if (amenity != null) {
                        predicates.add(criteriaBuilder.isMember(amenity, root.get(AMENITIES)));
                    } else {
                        return null;
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Amenity findAmenityByString(String amenityStr) {
        for (Amenity amenity : Amenity.values()) {
            if (amenity.toString().equalsIgnoreCase(amenityStr)) {
                return amenity;
            }
        }
        return null;
    }
}