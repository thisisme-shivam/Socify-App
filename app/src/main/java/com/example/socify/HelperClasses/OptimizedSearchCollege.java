package com.example.socify.HelperClasses;

import android.util.Log;
import android.view.View;

import com.example.socify.Activities.Registration;
import com.example.socify.Classes.College;
import com.example.socify.RegistrationFragments.GetCollegeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptimizedSearchCollege {
    public  ArrayList<College> filterlist;
    public Thread th;
    boolean stopthread;
    Registration regActivity;
    Map<String,ArrayList<College>> hashMap;
    GetCollegeFragment getCollegeFragment;

    public ArrayList<College> newfilterlist;
    public OptimizedSearchCollege(GetCollegeFragment getCollegeFragment){
        regActivity = (Registration) getCollegeFragment.getActivity();
        filterlist = regActivity.colleges;
        hashMap = new HashMap<>();
        this.getCollegeFragment = getCollegeFragment;
    }


    String oldtext = "";
    public void startSearch(String newText){
        // if new text is empty no need to search colleges directly assign origin list to adapter
        if(newText.isEmpty()){
            getCollegeFragment.adapter.filterlist(regActivity.colleges);
            return;
        }


        String[] keywords= newText.trim().split("\\s+");
        if(hashMap.get(keywords[0]) != null) {
            filterlist = hashMap.get(keywords[0]);
            Log.i("this entering","yes");

        }
        else if(newText.contains(oldtext) && !oldtext.isEmpty() )
            filterlist = newfilterlist;
        else
            filterlist = regActivity.colleges;



        newfilterlist = new ArrayList<>();

        th =new Thread(new Runnable() {
            @Override
            public void run() {
                stopthread  = false;
                for(College college : filterlist ){
                    if(stopthread)
                        break;

                    String[] collegewords = college.getCollege_name().split(" ");

                    if(check(keywords,collegewords) == keywords.length)
                        newfilterlist.add(college);

                }
                if(!stopthread) {
                    for(String s:keywords)
                        hashMap.put(newText, newfilterlist);

                    getCollegeFragment.hand.post(new Runnable() {
                        @Override
                        public void run() {
                            getCollegeFragment.adapter.filterlist(newfilterlist);
                            notifyUi();
                            oldtext = newText;
                        }
                    });
                }

            }
        });
        th.start();
    }


    public void stopRunningThread(){
        stopthread = true;
    }

    public  static int check(String[] keywords,String[] string){
        int i=0;
        for(String s:keywords){
            for(String c : string){
                if(c.toLowerCase().startsWith(s.toLowerCase())){
                    i++;
                    break;
                }
            }
        }
        return i;
    }
    public void notifyUi(){
        getCollegeFragment.layout.setVisibility(View.GONE);
        getCollegeFragment.layout.stopShimmer();
        getCollegeFragment.rec.setVisibility(View.VISIBLE);
    }

}
