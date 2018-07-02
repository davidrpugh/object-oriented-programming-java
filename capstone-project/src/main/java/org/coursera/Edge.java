package org.coursera;


/** Class representing an Edge in the Bitcoin network.
 *
 * @author davidrpugh
 */
public class Edge {

    private int rating;
    private int sourceId;
    private int targetId;
    private double timestamp;
    
    Edge(int sourceId, int targetId, int rating, double timestamp) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    /** The source node's rating for the target node.
     *
     * @return the source node's rating of the target node.
     */
    public int getRating() {
        return rating;
    }

    /** The node identifier of the source, i.e., rater.
     * 
     * @return node identifier of the user providing the rating.
     */
    public int getSourceId() {
        return sourceId;
    }

    /** The node identifier of the target, i.e., ratee.
     * 
     * @return node identifier of the user being rated.
     */
    public int getTargetId() {
        return targetId;
    }

    /** The timestamp associated with the rating.
     * 
     * @return the timestamp associated with the rating.
     */
    public double getTimestamp() {
        return timestamp;
    }

}
