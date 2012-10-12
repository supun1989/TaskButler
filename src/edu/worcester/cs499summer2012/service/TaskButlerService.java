/*
 * TasksBatlerService.java
 * 
 * Copyright 2012 Jonathan Hasenzahl, James Celona, Dhimitraq Jorgji
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.worcester.cs499summer2012.service;

import edu.worcester.cs499summer2012.database.TasksDataSource;
import edu.worcester.cs499summer2012.task.Task;

import java.util.ArrayList;
import java.util.ListIterator;

import android.content.Intent;
import android.util.Log;

/**
 * An IntentService that takes care of setting up alarms for Task Butler
 * to remind the user of upcoming events
 * @author Dhimitraq Jorgji
 *
 */
public class TaskButlerService extends WakefulIntentService{
	
	
	public TaskButlerService() {
		super("TaskButlerService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("TAskButlerService","in onHandleIntent");
		TasksDataSource db = TasksDataSource.getInstance(getApplicationContext()); //get access to the instance of TasksDataSource
		//NotificationHelper notification = new NotificationHelper();//for testing
		ArrayList<Task> tasks = db.getAllTasks(); //Get a list of all the tasks there
		ListIterator<Task> iterator = tasks.listIterator(); //get an ListIterator over the list
		TaskAlarm alarm = new TaskAlarm();
	 		 	
		while(iterator.hasNext()){ 
			Task task = iterator.next();
			
			if(!task.isCompleted() && (task.getDateDue() >= System.currentTimeMillis()))
				alarm.setOnetimeAlarm(getApplicationContext(), task.getID());
			//notification.sendBasicNotification(getApplicationContext(), task.getID()); //for testing.
			Log.d("Items in iterator", " "+task);
		}
		super.onHandleIntent(intent);
	}
}