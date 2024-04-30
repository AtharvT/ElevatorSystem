### Elevator System Design Problem for a Medium-Sized Building

#### Context
Design a simple yet effective elevator control system for a 20-floor office building with 4 elevators, including two basement levels.

#### Building Specifications
- **Number of Floors**: 20 floors (B2 to Floor 20).
- **Number of Elevators**: 4.

#### Functional Requirements

1. **Elevator Movement**:
    - Elevators move up and down across all floors, stopping based on incoming requests efficiently.

2. **Handling Requests**:
    - **Internal Requests**: Requests from inside the elevator.
    - **External Requests**: Requests from the floors for upward or downward movement.
    - Elevators are dynamically assigned based on proximity and current direction.

3. **Request Management**:
    - Prioritize requests to optimize travel and minimize wait times, handling directions efficiently.

4. **System Operation**:
    - Handle real-time requests, dynamically updating routes.
    - Return to strategic positions when idle.

#### Non-functional Requirements

1. **Efficiency**:
    - Optimize wait and travel times.

2. **Scalability**:
    - Accommodate future increases in floors or elevators.

3. **Reliability**:
    - Ensure continuous operation with minimal disruptions.

#### Design Considerations
- Develop algorithms for dynamic request handling and elevator dispatch.
- Architect a system that supports real-time control and monitoring.
- Ensure flexibility to adapt to various traffic patterns and use cases.

This concise summary captures the essence of the elevator system design problem, tailored for a technical interview, focusing on the core functional and non-functional requirements necessary for a comprehensive understanding and discussion.