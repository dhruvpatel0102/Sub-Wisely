package com.example.sub_wisely;
import android.os.Parcel;
import android.os.Parcelable;

public class SubRVModal implements Parcelable {
    // creating variables for our different fields.
    private String subName;
    private String subDesc;
    private String subPrice;
    private String paidOnBasis;
    /*private String courseImg;
    private String courseLink;*/
    private String subId;



    public String getSubId() {

        return subId;
    }

    public void setSubId(String subId) {

        this.subId = subId;
    }


    // creating an empty constructor.
    public SubRVModal(String subId, String subName, String subDesc, String subPrice, String paidOnBasis) {

    }

    protected SubRVModal(Parcel in) {
        subName = in.readString();
        subId = in.readString();
        subDesc = in.readString();
        subPrice = in.readString();
        paidOnBasis = in.readString();
      /*  courseImg = in.readString();
        courseLink = in.readString();*/
    }

    public static final Creator<SubRVModal> CREATOR = new Creator<SubRVModal>() {
        @Override
        public SubRVModal createFromParcel(Parcel in) {

            return new SubRVModal(in);
        }

        @Override
        public SubRVModal[] newArray(int size) {
            return new SubRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getSubName() {

        return subName;
    }

    public void setSubName(String subName) {

        this.subName = subName;
    }

    public String getSubDesc() {

        return subDesc;
    }

    public void setSubDesc(String subDesc) {

        this.subDesc = subDesc;
    }

    public String getSubPrice() {

        return subPrice;
    }

    public void setSubPrice(String subPrice) {

        this.subPrice = subPrice;
    }

    public String getPaidOnBasis() {

        return paidOnBasis;
    }

    public void setPaidOnBasis(String paidOnBasis) {

        this.paidOnBasis = paidOnBasis;
    }

  /*  public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }*/


    public SubRVModal(String subId, String subName, String subDesc, String subPrice, String paidOnBasis, String courseImg, String courseLink) {
        this.subName = subName;
        this.subId = subId;
        this.subDesc = subDesc;
        this.subPrice = subPrice;
        this.paidOnBasis = paidOnBasis;
       /* this.courseImg = courseImg;
        this.courseLink = courseLink;*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subName);
        dest.writeString(subId);
        dest.writeString(subDesc);
        dest.writeString(subPrice);
        dest.writeString(paidOnBasis);
       /* dest.writeString(courseImg);
        dest.writeString(courseLink);*/
    }
}
