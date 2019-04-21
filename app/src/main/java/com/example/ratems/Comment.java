package com.example.ratems;

public class Comment {

        private String CommentText;
        private String image;
        private String name;
        private String userId;

        String time;
        public Comment(){
            super();
        }
        public Comment(String CommentText,String name,String userId,String time)
        {
            this.name=name;
            this.CommentText=CommentText;
            this.userId=userId;
            this.time=time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



        public String getCommentText() {
            return CommentText;
        }

        public void setCommentText(String CommentText) {
            this.CommentText = CommentText;
        }


        public String getTime(){
            return time;
        }
        public void setTime(String time){
            this.time=time;
        }
        public String getUserId(){
          return userId;
        }
        public void setUserId(String userId){
            this.userId=userId;
        }
}
