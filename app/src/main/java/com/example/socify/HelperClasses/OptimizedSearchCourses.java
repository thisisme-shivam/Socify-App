package com.example.socify.HelperClasses;

import android.view.View;

import com.example.socify.Activities.Registration;
import com.example.socify.Classes.Course;
import com.example.socify.RegistrationFragments.CoursesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptimizedSearchCourses {
    public  ArrayList<Course> filterlist;
    Thread th;
    boolean stopthread;
    Map<String,ArrayList<Course>> hashMap;
    CoursesFragment coursesFragment;

    public  ArrayList<Course> newfilterlist;
    public OptimizedSearchCourses(CoursesFragment coursesFragment){

        hashMap = new HashMap<>();
        this.coursesFragment = coursesFragment;
        filterlist = coursesFragment.regActivity.courses;
    }

    String oldtext = "";
    public void startSearch(String newText){

        if(newText.isEmpty()){
            newfilterlist = coursesFragment.regActivity.courses;
            coursesFragment.adapter.filterlist(coursesFragment.regActivity.courses);
            coursesFragment.shimmerFrameLayout.setVisibility(View.GONE);
            coursesFragment.rec.setVisibility(View.VISIBLE);
            return;
        }

         if(hashMap.get(newText) != null) {
            filterlist = hashMap.get(newText);
        }else if(newText.contains(oldtext) && !oldtext.isEmpty())
            filterlist = newfilterlist;
        else
            filterlist = coursesFragment.regActivity.courses;

        stopthread  = false;
        newfilterlist = new ArrayList<>();
        th =new Thread(new Runnable() {
            @Override
            public void run() {
                for(Course course : filterlist ){
                    if(stopthread)
                        break;

                    String[] keywords = newText.split(" ");
                    String[] collegewords = course.getcoursename().split(" ");

                    if(OptimizedSearchCollege.check(keywords,collegewords) == keywords.length)
                        newfilterlist.add(course);

                }

                if(!stopthread) {
                    hashMap.put(newText, newfilterlist);

                    coursesFragment.hand.post(new Runnable() {
                        @Override
                        public void run() {
                            oldtext = newText;
                            coursesFragment.adapter.filterlist(newfilterlist);
                            coursesFragment.shimmerFrameLayout.setVisibility(View.GONE);
                            coursesFragment.rec.setVisibility(View.VISIBLE);
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




}
