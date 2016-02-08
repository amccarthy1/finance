package me.amccarthy.finance.places;

import me.amccarthy.finance.messages.MessageService;
import sun.plugin2.message.Message;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public enum PlaceType {
    // This list is prioritized by importance. For example, if a particular place
    // is both a gas station and car wash, it will be categorized under gas station.
    RESTAURANT("restaurant", "finance.groups.restaurant"),
    MEAL_DELIVERY("meal_delivery", "finance.groups.restaurant"),
    MEAL_TAKEAWAY("meal_takeaway", "finance.groups.restaurant"),
    ATM("atm", "finance.groups.atm"),
    BANK("bank", "finance.groups.bank"),
    CAFE("cafe", "finance.groups.restaurant"),
    BUS_STATION("bus_station", "finance.groups.publicTransit"),
    SUBWAY_STATION("subway_station", "finance.groups.publicTransit"),
    TRAIN_STATION("train_station", "finance.groups.publicTransit"),
    TAXI_STAND("taxi_stand", "finance.groups.transportation"),
    FOOD("food", "finance.groups.food"),
    BAKERY("bakery", "finance.groups.food"),
    LIQUOR_STORE("liquor_store", "finance.groups.food"),
    BICYCLE_STORE("bicycle_store", "finance.groups.hobby"),
    BOOK_STORE("book_store", "finance.groups.hobby"),
    BOWLING_ALLEY("bowling_alley", "finance.groups.hobby"),
    CAMPGROUND("campground", "finance.groups.hobby"),
    GENERAL_CONTRACTOR("general_contractor", "finance.groups.home"),
    HARDWARE_STORE("hardware_store", "finance.groups.home"),
    MOVING_COMPANY("moving_company", "finance.groups.home"),
    PAINTER("painter", "finance.groups.home"),
    REAL_ESTATE_AGENCY("real_estate_agency", "finance.groups.home"),
    ROOFING_CONTRACTOR("roofing_contractor", "finance.groups.home"),
    PLUMBER("plumber", "finance.groups.home"),
    ELECTRONICS_STORE("electronics_store", "finance.groups.hobby"),
    RV_PARK("rv_park", "finance.groups.hobby"),
    BEAUTY_SALON("beauty_salon", "finance.groups.beauty"),
    AMUSEMENT_PARK("amusement_park", "finance.groups.tourism"),
    AQUARIUM("aquarium", "finance.groups.tourism"),
    ART_GALLERY("art_gallery", "finance.groups.tourism"),
    MUSEUM("museum", "finance.groups.tourism"),
    PARK("park", "finance.groups.tourism"),
    TRAVEL_AGENCY("travel_agency", "finance.groups.tourism"),
    ZOO("zoo", "finance.groups.tourism"),
    AIRPORT("airport", "finance.groups.airport"),
    BAR("bar", "finance.groups.bar"),
    CAR_DEALER("car_dealer", "finance.groups.automotive"),
    CAR_RENTAL("car_rental", "finance.groups.automotive"),
    CAR_REPAIR("car_repair", "finance.groups.automotive"),
    CAR_WASH("car_wash", "finance.groups.automotive"),
    CASINO("casino", "finance.groups.gambling"),
    CLOTHING_STORE("clothing_store", "finance.groups.store"),
    CONVENIENCE_STORE("convenience_store", "finance.groups.store"),
    DENTIST("dentist", "finance.groups.health"),
    DEPARTMENT_STORE("department_store", "finance.groups.departmentStore"),
    DOCTOR("doctor", "finance.groups.health"),
    ELECTRICIAN("electrician", "finance.groups.utilities"),
    FLORIST("florist", "finance.groups.store"),
    FURNITURE_STORE("furniture_store", "finance.groups.store"),
    GAS_STATION("gas_station", "finance.groups.gas"),
    GROCERY_OR_SUPERMARKET("grocery_or_supermarket", "finance.groups.grocery"),
    GYM("gym", "finance.groups.health"),
    HAIR_CARE("hair_care", "finance.groups.beauty"),
    HEALTH("health", "finance.groups.health"),
    HOME_GOODS_STORE("home_goods_store", "finance.groups.store"),
    HOSPITAL("hospital", "finance.groups.health"),
    INSURANCE_AGENCY("insurance_agency", "finance.groups.insurance"),
    JEWELRY_STORE("jewelry_store", "finance.groups.store"),
    LAUNDRY("laundry", "finance.groups.laundry"),
    LAWYER("lawyer", "finance.groups.legal"),
    LIBRARY("library", "finance.groups.library"),
    LOCAL_GOVERNMENT_OFFICE("local_government_office", "finance.groups.legal"),
    LOCKSMITH("locksmith", "finance.groups.locksmith"),
    LODGING("lodging", "finance.groups.lodging"),
    MOVIE_RENTAL("movie_rental", "finance.groups.entertainment"),
    MOVIE_THEATER("movie_theater", "finance.groups.entertainment"),
    NIGHT_CLUB("night_club", "finance.groups.bar"),
    PARKING("parking", "finance.groups.parking"),
    PET_STORE("pet_store", "finance.groups.pets"),
    PHARMACY("pharmacy", "finance.groups.health"),
    PHYSIOTHERAPIST("physiotherapist", "finance.groups.health"),
    POST_OFFICE("post_office", "finance.groups.post"),
    SCHOOL("school", "finance.groups.school"),
    SHOE_STORE("shoe_store", "finance.groups.store"),
    SHOPPING_MALL("shopping_mall", "finance.groups.store"),
    SPA("spa", "finance.groups.beauty"),
    STADIUM("stadium", "finance.groups.entertainment"),
    STORAGE("storage", "finance.groups.storage"),
    STORE("store", "finance.groups.store"),
    UNIVERSITY("university", "finance.groups.school"),
    VETERINARY_CARE("veterinary_care", "finance.groups.pets"),
    FINANCE("finance", "finance.groups.finance"),
    ACCOUNTING("accounting", "finance.groups.finance"),
    ONLINE("online", "finance.groups.online"),
    UNKNOWN("unknown", "finance.groups.unknown")
    ;

    private String apiType;
    private String msgCode;

    PlaceType(String apiType, String msgCode) {
        this.apiType = apiType;
        this.msgCode = msgCode;
    }

    public static PlaceType reverse(String apiType) {
        try {
            return valueOf(apiType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    public static PlaceType reverse(String[] apiTypes) {
        PlaceType minPriority = UNKNOWN;
        for (String type : apiTypes) {
            PlaceType t = reverse(type);
            if (t.ordinal() < minPriority.ordinal()) {
                minPriority = t;
            }
        }
        return minPriority;
    }

    public String getMessage() {
        return MessageService.getInstance().getMessage(msgCode);
    }
}
