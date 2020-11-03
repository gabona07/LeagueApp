package com.example.leagueapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

public class ChampionResponse {
    public Map<String, Champion> data;


    public class Champion implements Parcelable {
        String id;
        String name;
        String title;
        Image image;
        List<String> tags;

        protected Champion(Parcel in) {
            id = in.readString();
            name = in.readString();
            title = in.readString();
            image = in.readParcelable(Image.class.getClassLoader());
            tags = in.createStringArrayList();
        }

        public final Creator<Champion> CREATOR = new Creator<Champion>() {
            @Override
            public Champion createFromParcel(Parcel in) {
                return new Champion(in);
            }

            @Override
            public Champion[] newArray(int size) {
                return new Champion[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(name);
            parcel.writeString(title);
            parcel.writeParcelable(image, i);
            parcel.writeStringList(tags);
        }

        public class Image implements Parcelable {
            String full;

            protected Image(Parcel in) {
                full = in.readString();
            }

            public final Creator<Image> CREATOR = new Creator<Image>() {
                @Override
                public Image createFromParcel(Parcel in) {
                    return new Image(in);
                }

                @Override
                public Image[] newArray(int size) {
                    return new Image[size];
                }
            };

            public String getIconUrl() {
                return "https://ddragon.leagueoflegends.com/cdn/10.21.1/img/champion/" + full;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(full);
            }
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public Image getImage() {
            return image;
        }

        public String getTags() {
            return TextUtils.join(", ", tags);
        }

    }
}
