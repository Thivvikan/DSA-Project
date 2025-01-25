class CustomMap {
    private static class Entry {
        String schedule;
        CustomQueue queue;
        Entry next;
        
        Entry(String schedule, CustomQueue queue) {
            this.schedule = schedule;
            this.queue = queue;
            this.next = null;
        }
    }

    private Entry head;

    public void put(String schedule, CustomQueue queue) {
        Entry current = head;
        while (current != null) {
            if (current.schedule.equals(schedule)) {
                current.queue = queue;
                return;
            }
            current = current.next;
        }
        Entry newEntry = new Entry(schedule, queue);
        newEntry.next = head;
        head = newEntry;
    }

    public CustomQueue get(String schedule) {
        Entry current = head;
        while (current != null) {
            if (current.schedule.equals(schedule)) {
                return current.queue;
            }
            current = current.next;
        }
        return null;
    }
}