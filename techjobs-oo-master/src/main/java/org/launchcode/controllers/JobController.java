package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job singleJob = jobData.findById(id);
        model.addAttribute("job", singleJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (jobForm.getName().length() == 0){
            model.addAttribute("errors", errors);
        }
        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }

            Job newJob = new Job(jobForm.getName(),

                jobData.getEmployers().findById(jobForm.getEmployerId()),
                jobData.getLocations().findById(jobForm.getLocationId()),
                jobData.getPositionTypes().findById(jobForm.getPositionTypeId()),
                jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));

       // String name = jobForm.getName();
       // Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
      //  Location location = jobData.getLocations().findById(jobForm.getLocationId());
     //   CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
      //  PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

       // Job newJob = new Job(name, employer, location, positionType, coreCompetency);

        jobData.add(newJob);


        Job newId = jobData.findById(newJob.getId());
        Integer newJobIdNum = newJob.getId();
        model.addAttribute("job", newId);
        return "redirect:/job?id=" + newJobIdNum;

    }
}
