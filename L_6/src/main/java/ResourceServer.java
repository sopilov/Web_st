import resources.TestResource;

public class ResourceServer {
    private TestResource testResource;

    ResourceServer (TestResource testResource) {
        this.testResource = testResource;
    }

    ResourceServer () {
        this.testResource = new TestResource();
    }

    public void setTestResource (TestResource testResource) {
        this.testResource = testResource;
    }

    public String getName() {
        return testResource.getName();
    }

    public int getAge() {
        return testResource.getAge();
    }
}
