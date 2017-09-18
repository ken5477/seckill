package org.seckill.util.Java3Features;

/**
 * Created by Ken Pan on 2017/6/19.
 */
public class FengZhuangAndJicheng {

    public class Person {
        /*
         * 对属性的封装
         * 一个人的姓名、性别、年龄、妻子都是这个人的私有属性
         */
        private String name ;
        private String sex ;
        private int age ;

        /*
        * setter()、getter()是该对象对外开发的接口
        */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    //继承
    public class Husband extends Person{

        private Wife wife;

        public void setWife(Wife wife) {
            this.wife = wife;
        }
    }

    public class Wife extends Person{

        private Husband husband;

        public void setHusband(Husband husband) {
            this.husband = husband;
        }

        public Husband getHusband() {
            return husband;
        }

    }

}
