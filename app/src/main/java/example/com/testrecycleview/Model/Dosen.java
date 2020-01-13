package example.com.testrecycleview.Model;

import com.google.gson.annotations.SerializedName;

public class Dosen {
    @SerializedName("nama")
    private String nama;
    @SerializedName("id")
    private String id;
    @SerializedName("pelajaran")
    private String pelajaran;

    public Dosen(String nama, String id, String pelajaran) {
        this.nama = nama;
        this.id = id;
        this.pelajaran = pelajaran;
    }

    public String getNama ()
    {
        return nama;
    }

    public void setNama (String nama)
    {
        this.nama = nama;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPelajaran ()
    {
        return pelajaran;
    }

    public void setPelajaran (String pelajaran)
    {
        this.pelajaran = pelajaran;
    }
}
