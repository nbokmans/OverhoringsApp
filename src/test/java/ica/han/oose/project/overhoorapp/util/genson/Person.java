package ica.han.oose.project.overhoorapp.util.genson;

/**
 * @author Niels
 * @version 1.0
 * @since 7-6-2015
 */
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }
        final Person other = (Person) obj;
        return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName) &&
                this.age == other.age;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}