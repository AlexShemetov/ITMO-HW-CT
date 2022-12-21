package ru.itmo.wp.model.domain;

public class Event {
    private long id;
    private long userId;
    private Types type;

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Types getType() {
        return type;
    }

    public enum Types {
        ENTER(1), LOGOUT(2);

        private int num;
        Types(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}
