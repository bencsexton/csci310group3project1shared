package csci310.servlet.activity;

import java.io.IOException;
import csci310.servlet.activity.Activities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivityPlanningActivities extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        List<String> activities = Activities.getActivityList();
    }

}