package com.example.junhu.savelah.dataObjects;

    public class Calendar {
        private int year;
        private int month;
        private int dayOfMonth;
        private int hourOfDay;
        private int minute;
        private int second;

        Calendar() {}

       public Calendar(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.dayOfMonth = day;
            this.hourOfDay = 0;
            this.minute = 0;
            this.second = 0;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }
    }


