package app.ahiha.pro.ahihaapplication.MainActivityRes;

/**
 * Created by AF on 3/6/2017.
 */

public interface Json_getData {

     //get profile donor
     String getDonor_url = "http://ajwc.000webhostapp.com/Android_PHP/getDonor.php";

     String email_post = "email";
     String tag_json_array = "result";
     String tag_name = "name_donor";
     String tag_dateofbirth = "dateofbirth_donor";
     String tag_city = "city_donor";
     String tag_region = "region";
     String tag_sex_donor = "sex_donor";
     String tag_bloodtype = "bloodtype_donor";
     String tag_phone = "phone_donor";

    //Update Activity

    String updateDonor_URl = "http://ajwc.000webhostapp.com/Android_PHP/UpdateDonor.php";

    String name_donor = "name_p";
    String date_donor = "date_p";
    String city_donor = "city_donor";
    String phone = "phone";
    String bloodtype = "blood_type";
    String sex_donor = "sex_donor";
    String region_donor = "region";
    String email_donor = "email_p";

    //get calls
    String getCalls_Url = "http://ajwc.000webhostapp.com/Android_PHP/GetCalls.php";

    String name_hospital = "name_hospital";
    String patient_health = "patient_health";
    String num_blood = "num_blood";
    String address = "address";
    String start_date = "start_date";
    String end_date = "end_date";
    String ty_blood = "ty_blood";
    String phonetocall = "phonetocall";
    String notes = "notes";
    String jsot_tag_calls = "result";

    //get centers and hospital
    String getCenterurl = "http://ajwc.000webhostapp.com/Android_PHP/GetCenter.php";
    String center_name = "center_name";
    String centercity = "centercity";
    String centerregion = "centerregion";
    String centerstreetname = "centerstreetname";
    String centerphone = "centerphone";
    String tag_json_center = "result";


}
