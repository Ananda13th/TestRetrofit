package example.com.testrecycleview.model;

import java.util.List;

public class ResponseDosen extends BaseResponse{


    private List<Dosen> dosen_list;

    public ResponseDosen(List<Dosen> dosen_list) {

        this.dosen_list = dosen_list;
    }


    public List<Dosen> getDosenList() {
        return dosen_list;
    }

    public void setDosenList(List<Dosen> dosen_list) {
        this.dosen_list = dosen_list;
    }
}
