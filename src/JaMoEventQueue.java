import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class JaMoEventQueue implements IEventQueue {

    private ArrayList<JaMoEvent> events;

    public JaMoEventQueue(){
        events = new ArrayList<JaMoEvent>();
    }


    public void enqueue(Double time, Object event) {
        events.add(new JaMoEvent(time, event));
    }


    public Entry dequeue() {

        Entry latestEntry  = this.getLatestEvent();

        events.remove(latestEntry);

        return latestEntry;
    }

    public Entry getLatestEvent() {
        return events.stream()
                .min(Comparator.comparingDouble(JaMoEvent::getTime))
                .orElseThrow(NoSuchElementException::new);
    }


    public class JaMoEvent implements IEventQueue.Entry {

        private Double time;

        private Object event;

        public JaMoEvent(double time, Object event){
            this.time = time;
            this.event = event;
        }

        public Double getTime(){
            return time;
        }

        public Object getEvent(){
            return event;
        }

    }


}
