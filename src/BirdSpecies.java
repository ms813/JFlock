/**
 * Created by smithma on 27/01/15.
 */
public enum BirdSpecies {
    SPARROW(0.2f, 50.0f, 0.9999f, 50.0f),
    HAWK(1.0f, 49.0f, 10.0f, 250.0f);

    private float mass;
    private float maxSpeed;
    private float friction;
    private float localityRadius;
    private BirdMotionHandler motionHandler;

    BirdSpecies(float mass, float maxSpeed, float friction, float localityRadius){
        this.mass = mass;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        this.localityRadius = localityRadius;
    }

    public float getMass() {
        return mass;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getFriction() {
        return friction;
    }

    public float getLocalityRadius() {
        return localityRadius;
    }
}
