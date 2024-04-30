package org.elevatorsystem.model.request;

import org.elevatorsystem.model.Direction;

public class ExternalRequest extends Request {
    private Direction direction;

    public ExternalRequest(Direction direction, int floor, int numberOfPeople) {
        super(floor, numberOfPeople);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
