/**
 * Created by smithma on 27/01/15.
 */
public enum BirdSpecies {
    SPARROW(0.2f, 50.0f, 0.9999f, 50.0f, new SparrowMotionHandler()),
    HAWK(1.0f, 3.0f, 10.0f, 50.0f, new NullMotionHandler());

    private float mass;
    private float maxSpeed;
    private float friction;
    private float localityRadius;
    private BirdMotionHandler motionHandler;

    BirdSpecies(float mass, float maxSpeed, float friction, float localityRadius, BirdMotionHandler motionHandler){
        this.mass = mass;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        this.localityRadius = localityRadius;
        this.motionHandler = motionHandler;
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

    public BirdMotionHandler getMotionHandler(){ return motionHandler; }
}
