
package coop.biantik.traductor.network.beans;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import coop.biantik.traductor.model.Post;

public class Embedded {


    @Expose
    private List<Post> jobs = new ArrayList<Post>();


    public List<Post> getJobs() {
        return jobs;
    }

    public void setJobs(List<Post> jobs) {
        this.jobs = jobs;
    }
}
