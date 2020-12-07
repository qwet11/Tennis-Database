/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

// Desc.: Queue of TennisPlayers (reference-based queue)
class TennisPlayerQueue implements TennisPlayerQueueInterface {
    private TennisPlayer[] players;
    private int frontIndex; // Index of the queue's front
    private int backIndex; // Index of the queue's back
    private int numPlayers; // Number of players in queue

    public TennisPlayerQueue() {
        this.players = new TennisPlayer[20]; // Starting size of the queue is 20
        this.frontIndex = this.numPlayers = 0;
        this.backIndex = -1; // For the first enqueue
    }

    // Desc.: Check if the queue is empty.
    // Output: True or false.
    public boolean isEmpty() {
        return this.numPlayers == 0;
    }

    // Desc.: Insert a tennis player at the back of this queue.
    // Input: A tennis player.
    // Output: Throws a checked (critical) exception if the insertion fails.
    public void enqueue(TennisPlayer p) {
        // Checks if the queue needs to be resized (only has 1 space left)
        if (this.numPlayers == this.players.length - 1) {
            resizeQueue();
        }

        this.backIndex = (this.backIndex + 1) % this.players.length;
        this.players[backIndex] = p;
        this.numPlayers++;
    }

    // Desc.: Doubles the size of the queue and resets the frontIndex to 0
    private void resizeQueue() {
        TennisPlayer[] tempArray = new TennisPlayer[this.players.length * 2];
        int currIndex = this.frontIndex;
        for (int i = 0; i < this.players.length; i++) {
            tempArray[i] = this.players[currIndex];
            currIndex = (currIndex + 1) % this.players.length;
        }
        this.players = tempArray;

        // Resets the frontIndex and backIndex
        this.frontIndex = 0;
        this.backIndex = this.numPlayers - 1;
    }

    // Desc.: Extract (return and remove) a tennis player from the front of this
    // queue.
    // Output: Throws a checked (critical) exception if the extraction fails.
    public TennisPlayer dequeue() throws TennisDatabaseException {
        if (!isEmpty()) {
            TennisPlayer frontPlayer = this.players[this.frontIndex];
            this.frontIndex = (this.frontIndex + 1) % this.players.length;
            this.numPlayers--;
            return frontPlayer;
        } else {
            throw new TennisDatabaseException("TennisPlayerQueue: Extraction failed");
        }
    }

    // Desc.: Return (without removing) the tennis player at the front of this
    // queue.
    // Output: Throws a checked (critical) exception if the queue is empty.
    public TennisPlayer peek() throws TennisDatabaseException {
        if (!isEmpty()) {
            return this.players[this.frontIndex];
        } else {
            throw new TennisDatabaseException("TennisPlayerQueue: Queue is empty");
        }
    }

}
