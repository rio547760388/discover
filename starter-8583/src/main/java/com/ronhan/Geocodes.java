package com.ronhan;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/4
 **/
public class Geocodes {
    private static Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put("4", "Afghanistan");
        map.put("248", "Aland Islands");
        map.put("8", "Albania");
        map.put("12", "Algeria");
        map.put("16", "American Samoa");
        map.put("20", "Andorra");
        map.put("24", "Angola");
        map.put("660", "Anguilla");
        map.put("10", "Antarctica");
        map.put("28", "Antigua and Barbuda");
        map.put("32", "Argentina");
        map.put("51", "Armenia");
        map.put("533", "Aruba (Lesser Antilles)");
        map.put("36", "Australia");
        map.put("40", "Austria");
        map.put("31", "Azerbaijan");
        map.put("44", "Bahamas");
        map.put("48", "Bahrain");
        map.put("50", "Bangladesh");
        map.put("52", "Barbados");
        map.put("112", "Belarus");
        map.put("56", "Belgium");
        map.put("84", "Belize");
        map.put("204", "Benin");
        map.put("60", "Bermuda");
        map.put("64", "Bhutan");
        map.put("68", "Bolivia");
        map.put("535", "Bonaire");
        map.put("70", "Bosnia and Herzegovina");
        map.put("72", "Botswana");
        map.put("74", "Bouvet Island");
        map.put("76", "Brazil");
        map.put("86", "British Indian Ocean Territory");
        map.put("96", "Brunei Darussalam");
        map.put("100", "Bulgaria");
        map.put("854", "Burkina Faso");
        map.put("108", "Burundi");
        map.put("116", "Cambodia");
        map.put("120", "Cameroon");
        map.put("124", "Canada");
        map.put("132", "Cape Verde");
        map.put("136", "Cayman Islands");
        map.put("140", "Central African Republic");
        map.put("148", "Chad");
        map.put("152", "Chile");
        map.put("156", "China");
        map.put("162", "Christmas Island");
        map.put("166", "Cocos (Keeling) Islands");
        map.put("170", "Colombia");
        map.put("174", "Comoros");
        map.put("178", "Congo");
        map.put("184", "Cook Islands");
        map.put("188", "Costa Rica");
        map.put("384", "Cote d'Ivoire (Ivory Coast)");
        map.put("191", "Croatia");
        map.put("192", "Cuba*");
        map.put("531", "Curacao");
        map.put("196", "Cyprus");
        map.put("203", "CZECHIA");
        map.put("180", "Democratic Republic of the Congo (Zaire)");
        map.put("208", "Denmark");
        map.put("262", "Djibouti");
        map.put("212", "Dominica");
        map.put("214", "Dominican Republic");
        map.put("218", "Ecuador");
        map.put("818", "Egypt");
        map.put("222", "El Salvador");
        map.put("226", "Equatorial Guinea");
        map.put("232", "Eritrea");
        map.put("233", "Estonia");
        map.put("231", "Ethiopia");
        map.put("234", "Faeroe Islands");
        map.put("238", "Falkland Islands (Malvinas)");
        map.put("706", "Federal Republic of Somalia ");
        map.put("242", "Fiji");
        map.put("246", "Finland");
        map.put("250", "France");
        map.put("254", "French Guiana");
        map.put("258", "French Polynesia");
        map.put("260", "French Southern Territories");
        map.put("266", "Gabon");
        map.put("270", "Gambia");
        map.put("268", "Georgia");
        map.put("276", "Germany");
        map.put("288", "Ghana");
        map.put("292", "Gibraltar");
        map.put("300", "Greece");
        map.put("304", "Greenland");
        map.put("308", "Grenada");
        map.put("312", "Guadeloupe");
        map.put("316", "Guam");
        map.put("320", "Guatemala");
        map.put("831", "Guernsey, The Isle of");
        map.put("324", "Guinea");
        map.put("624", "Guinea-Bissau");
        map.put("328", "Guyana");
        map.put("332", "Haiti");
        map.put("334", "Heard and McDonald Islands");
        map.put("340", "Honduras");
        map.put("344", "Hong Kong");
        map.put("348", "Hungary");
        map.put("352", "Iceland");
        map.put("598", "Papua New Guinea");
        map.put("356", "India");
        map.put("360", "Indonesia");
        map.put("368", "Iraq");
        map.put("372", "Ireland");
        map.put("376", "Israel");
        map.put("380", "Italy");
        map.put("388", "Jamaica");
        map.put("392", "Japan");
        map.put("832", "Jersey, The Bailiwick of");
        map.put("400", "Jordan");
        map.put("398", "Kazakhstan");
        map.put("404", "Kenya");
        map.put("296", "Kiribati");
        map.put("408", "Korea, Democratic People's Republic");
        map.put("410", "Korea, Republic of");
        map.put("414", "Kuwait");
        map.put("417", "Kyrgyzstan");
        map.put("418", "Lao People's Democratic Republic");
        map.put("428", "Latvia");
        map.put("422", "Lebanon");
        map.put("426", "Lesotho");
        map.put("430", "Liberia");
        map.put("434", "Libyan Arab Jamahiriya");
        map.put("438", "Liechtenstein");
        map.put("440", "Lithuania");
        map.put("833", "lsle of Man");
        map.put("442", "Luxembourg");
        map.put("446", "Macau");
        map.put("450", "Madagascar");
        map.put("454", "Malawi");
        map.put("458", "Malaysia");
        map.put("462", "Maldives");
        map.put("466", "Mali");
        map.put("470", "Malta");
        map.put("584", "Marshall Islands");
        map.put("474", "Martinique");
        map.put("478", "Mauritania");
        map.put("480", "Mauritius");
        map.put("175", "Mayotte");
        map.put("484", "Mexico");
        map.put("583", "Micronesia");
        map.put("498", "Moldova, Republic of");
        map.put("492", "Monaco");
        map.put("496", "Mongolia");
        map.put("499", "Montenegro, Republic of");
        map.put("500", "Montserrat");
        map.put("504", "Morocco");
        map.put("508", "Mozambique");
        map.put("104", "Myanmar");
        map.put("516", "Namibia");
        map.put("520", "Nauru");
        map.put("524", "Nepal");
        map.put("528", "Netherlands");
        map.put("530", "Netherlands Antilles");
        map.put("540", "New Caledonia");
        map.put("554", "New Zealand");
        map.put("558", "Nicaragua");
        map.put("562", "Niger");
        map.put("566", "Nigeria");
        map.put("570", "Niue");
        map.put("574", "Norfolk Island");
        map.put("580", "Northern Mariana Islands");
        map.put("578", "Norway");
        map.put("512", "Oman");
        map.put("586", "Pakistan");
        map.put("585", "Palau");
        map.put("591", "Panama");
        map.put("600", "Paraguay");
        map.put("604", "Peru");
        map.put("608", "Philippines");
        map.put("612", "Pitcairn");
        map.put("616", "Poland");
        map.put("620", "Portugal");
        map.put("630", "Puerto Rico");
        map.put("634", "Qatar");
        map.put("807", "Republic of Macedonia");
        map.put("638", "Reunion");
        map.put("642", "Romania");
        map.put("643", "Russian Federation");
        map.put("646", "Rwanda");
        map.put("882", "Samoa");
        map.put("674", "San Marino");
        map.put("678", "Sao Tome and Principe");
        map.put("682", "Saudi Arabia");
        map.put("686", "Senegal");
        map.put("688", "Serbia, Republic of");
        map.put("690", "Seychelles");
        map.put("694", "Sierra Leone");
        map.put("702", "Singapore");
        map.put("534", "Sint Maarten");
        map.put("703", "Slovakia");
        map.put("705", "Slovenia");
        map.put("90", "Solomon Islands");
        map.put("710", "South Africa");
        map.put("239", "South Georgia and the Sandwich Islands");
        map.put("728", "South Sudan");
        map.put("724", "Spain");
        map.put("144", "Sri Lanka");
        map.put("652", "St. Bart  (St. Barthelemy)");
        map.put("654", "St. Helena");
        map.put("659", "St. Kitts and Nevis");
        map.put("662", "St. Lucia");
        map.put("663", "St. Martin");
        map.put("666", "St. Pierre and Miquelon");
        map.put("670", "St. Vincent and the Grenadines");
        map.put("729", "Sudan");
        map.put("740", "Suriname");
        map.put("744", "Svalbard and Jan Mayen Islands");
        map.put("748", "Swaziland");
        map.put("752", "Sweden");
        map.put("756", "Switzerland");
        map.put("760", "Syrian Arab Republic");
        map.put("158", "Taiwan");
        map.put("762", "Tajikistan");
        map.put("834", "Tanzania, United Republic of");
        map.put("764", "Thailand");
        map.put("626", "Timor-Leste");
        map.put("768", "Togo");
        map.put("772", "Tokelau");
        map.put("776", "Tonga");
        map.put("780", "Trinidad and Tobago");
        map.put("788", "Tunisia");
        map.put("792", "Turkey");
        map.put("795", "Turkmenistan");
        map.put("796", "Turks and Caicos Islands");
        map.put("798", "Tuvalu");
        map.put("800", "Uganda");
        map.put("804", "Ukraine");
        map.put("784", "United Arab Emirates");
        map.put("826", "United Kingdom");
        map.put("840", "United States");
        map.put("581", "United States Minor Outlying Islands");
        map.put("858", "Uruguay");
        map.put("860", "Uzbekistan");
        map.put("548", "Vanuatu");
        map.put("336", "Vatican City State (Holy See)");
        map.put("862", "Venezuela");
        map.put("704", "Viet Nam");
        map.put("92", "Virgin Islands, British");
        map.put("850", "Virgin Islands, United States");
        map.put("876", "Wallis and Futuna Islands");
        map.put("732", "Western Sahara");
        map.put("887", "Yemen");
        map.put("894", "Zambia");
        map.put("716", "Zimbabwe");
    }

    public static String getCountry(String geocode) {
        return map.get(geocode);
    }
}
