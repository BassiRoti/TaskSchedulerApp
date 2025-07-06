# Task Scheduler Android App

This is a native Android application designed to help users manage their daily tasks. The app allows task categorization, deadline tracking, and scheduled notifications for better task planning and time management.

---

## Features

- Add, update, delete, and mark tasks as completed
- Categorize tasks (e.g., Work, Personal, Learning)
- Filter and sort tasks by category and deadline
- Receive notifications for upcoming task deadlines
- View completed/past tasks in a separate section
- Supports both light and dark modes

---

## Tech Stack

| Layer          | Technology                    |
|----------------|-------------------------------|
| Language       | Java                          |
| UI             | XML (Android Views)           |
| Database       | SQLite via SQLiteOpenHelper   |
| Notifications  | AlarmManager, NotificationManager |
| Architecture   | Fragment-based modular design |

---

## Project Structure

```

com.example.taskschedulerapp/
│
├── fragments/
│   ├── AddTaskFragment.java
│   ├── PastTabFragment.java
│   └── NotificationTabFragment.java
│
├── database/
│   ├── ScheduleDB.java
│   └── NotificationsDB.java
│
├── adapter/
│   └── TaskAdapter.java
│
├── model/
│   └── Task.java
│
├── MainActivity.java
└── ...

````

---

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/task-scheduler-app.git](https://github.com/BassiRoti/TaskSchedulerApp.git
````

2. Open the project in Android Studio:

   * File > Open > Navigate to the cloned folder
   * Allow Gradle to sync

3. Run on emulator or physical device:

   * Select a target device (API 21+ recommended)
   * Click Run

---

## Assumptions

* Deadlines are managed using system time and checked against stored date and time.
* Notifications are triggered using Android's AlarmManager, which does not persist alarms after a device reboot unless reconfigured.
* The app assumes a single-user local setup without authentication.
* Input validation is basic; incorrect formats may cause unexpected behavior.

---

## Potential Improvements

* Enable custom notification times (e.g., notify X minutes before deadline)
* Integrate Firebase for remote data storage and multi-device sync
* Implement login/authentication for personalized task management
* Add backup/restore functionality
* Add support for recurring tasks
* Add testing (unit and UI tests)
* Improve accessibility and localization support

---

## License

This project is open source and licensed 

---

## Author

\Abbas
\https://github.com/BassiRoti

```
