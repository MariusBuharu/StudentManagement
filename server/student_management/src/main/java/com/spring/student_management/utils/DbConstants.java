package com.spring.student_management.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created at 11/12/2022 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DbConstants {
    //Lengths
    public static final int LENGTH_45 = 45;
    public static final int LENGTH_65 = 65;
    public static final int LENGTH_100 = 100;
    public static final int LENGTH_500 = 500;

    // Sequences
    public static final String SEQ_APP_USER = "seq_app_user";
    public static final String SEQ_INSTITUTION = "seq_institution";

    public static final String SEQ_ADDRESS = "seq_address";

    // Schemas
    public static final String SCHEMA_PUBLIC = "public";
    // Entities
    public static final String ENTITY_APP_USER = "AppUser";
    public static final String ENTITY_INSTITUTION = "Institution";
    public static final String ENTITY_ROLE = "Role";
    public static final String ENTITY_ADDRESS_TYPE = "AddressType";
    public static final String ENTITY_ADDRESS = "Address";
    // Tables
    public static final String TABLE_ADDRESS_TYPE = "address_type";
    public static final String TABLE_APP_USER = "app_user";
    public static final String TABLE_INSTITUTION = "institution";
    public static final String TABLE_ROLE = "role";
    public static final String TABLE_ADDRESS = "address";
    public static final String TABLE_APP_USER_INSTITUTION = "app_user_institution";

    // Columns
    public static final String ID = "id";
    public static final String ADDRESS_TYPE_ID = "address_type_id";
    public static final String ADDRESS_ID = "address_id";
    public static final String ROLE_ID = "role_id";
    public static final String INSTITUTION_ID = "institution_id";
    public static final String DATE_ADDED = "date_added";
    public static final String VERSION = "version";
    public static final String APP_USER_ID = "app_user_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PASSWORD = "password";
    public static final String DOB = "dob";
    public static final String IS_ACTIVE = "is_active";
    public static final String ROLE_NAME = "role_name";
    public static final String INSTITUTION_NAME = "institution_name";
    public static final String DESCRIPTION = "description";
    public static final String FOUNDED_DATE = "founded_date";
    public static final String COUNTRY = "COUNTRY";
    public static final String CITY = "CITY";
    public static final String TYPE = "type";
    public static final String ADDRESS_LINE_ONE = "ADDRESS_LINE_ONE";
    public static final String ADDRESS_LINE_TWO = "ADDRESS_LINE_TWO";
}
