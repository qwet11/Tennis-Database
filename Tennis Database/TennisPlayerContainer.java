/*
 * William Moss
 * CS-102, Fall 2020
 * Assignment 2
 * Last modified: Dec 5, 2020
 */

package TennisDatabase;

//Desc.: Container of TennisPlayers for database using BST
class TennisPlayerContainer implements TennisPlayerContainerInterface {
    private TennisPlayerContainerNode root;
    private int numPlayers;

    public TennisPlayerContainer() {
        this.numPlayers = 0;
    }

    // Desc.: Returns the number of players in this container.
    // Output: The number of players in this container as an integer.
    public int getNumPlayers() {
        return this.numPlayers;
    }

    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public TennisPlayerContainerIterator iterator() {
        return new TennisPlayerContainerIterator(this.root);
    }

    // Desc.: Search for a player in this container by input id, and returns a copy
    // of the node of that player (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player
    // with that input id.
    public TennisPlayerContainerNode getPlayerNode(String id) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode resultNode = getPlayerNodeRec(this.root, id);

        // Checks if the node wasn't found (only will happen if its null)
        if (resultNode == null) {
            throw new TennisDatabaseRuntimeException("TennisPlayerContainer: Player with id does not exist");
        }

        return resultNode;
    }

    // Desc.: Recursive implementation of getPlayerNode(String id)
    private TennisPlayerContainerNode getPlayerNodeRec(TennisPlayerContainerNode currNode, String id) {
        // Checks if currNode is null
        if (currNode == null) {
            return null;
        }

        // Checks if currNode is the player
        if (currNode.getPlayer().getId().equals(id)) {
            return currNode;
        }

        // Checks the left side
        TennisPlayerContainerNode checkLeftNodes = getPlayerNodeRec(currNode.getLeftChild(), id);
        if (checkLeftNodes != null) {
            // Only true if the node was found on the left
            return checkLeftNodes;
        }

        // Checks the right side
        TennisPlayerContainerNode checkRightNodes = getPlayerNodeRec(currNode.getRightChild(), id);
        if (checkRightNodes != null) {
            // Only true if the node was found on the right
            return checkRightNodes;
        }

        // Player was not found on either side of this node
        // The null should "bubble up" to the root node
        return null;
    }

    // Desc.: Search for a player in this container by input id, and returns a copy
    // of that player (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player
    // with that input id.
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        return getPlayerNode(id).getPlayer();
    }

    // Desc.: Insert a tennis player into this container.
    // Input: A tennis player.
    // Output: Throws a checked (critical) exception if player id is already in this
    // container.
    // Throws a checked (critical) exception if the container is full.
    public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
        this.root = insertPlayerRec(this.root, p);
        this.numPlayers++;
    }

    // Desc: Internal recursive implementation for the "insertPlayer" method
    // Input: currRoot: reference to root node of input BST
    // newPlayer, player to be inserted in input BST
    // Output: Reference to root node of input BST (could be the same as currRoot or
    // not)
    // Throws a checked (critical) exception if player id is already in this
    // container
    // Throws a checked (critical) exception if the container is full
    private TennisPlayerContainerNode insertPlayerRec(TennisPlayerContainerNode currRoot, TennisPlayer newPlayer)
            throws TennisDatabaseException {
        // Checks if input BST is empty
        if (currRoot == null) {
            // Input BST is empty, found point of insertion, (A) create new node to store
            // input
            // player, and (B) return it as new root of input BST
            TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(newPlayer); // (A)
            return newNode; // (B)
        } else {
            // Input BST is not empty, compare input player with player stored at currRoot,
            // and eventually proceed insertion in left/right subtree
            int comparisonResult = newPlayer.compareTo(currRoot.getPlayer());
            // Check result of 3-way comparison
            if (comparisonResult == 0) {
                // Input player is equal to player stored at currRoot, insertion failed because
                // input player id is already stored in input BST
                throw new TennisDatabaseException(
                        "TennisPlayerContainer: insert failed because input player id is already in database!");
            } else if (comparisonResult < 0) {
                // Input player is less than player stored at currRoot, proceed insertion in
                // left subtree of currRoot
                TennisPlayerContainerNode newLeftChild = insertPlayerRec(currRoot.getLeftChild(), newPlayer); // Recursive
                                                                                                              // call in
                                                                                                              // left
                                                                                                              // subtree
                                                                                                              // of
                                                                                                              // currRoot
                currRoot.setLeftChild(newLeftChild);
                return currRoot;
            } else {
                TennisPlayerContainerNode newRightChild = insertPlayerRec(currRoot.getRightChild(), newPlayer);
                currRoot.setRightChild(newRightChild);
                return currRoot;
            }
        }
    }

    // Desc.: Search for a player in this container by id, and delete it with all
    // his matches (if found).
    // Output: Throws a checked (critical) exception if there is no player with that
    // input id
    // Throws an unchecked (non-critical) exception if there is no match with the
    // player
    public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException, TennisDatabaseException {
        try {
            // Checks if the player exists
            getPlayerNode(playerId);
        } catch (TennisDatabaseRuntimeException e) {
            throw new TennisDatabaseException("TennisPlayerContainer: No player found with given id");
        }
        deletePlayerRec(this.root, playerId); // Delete the node
        numPlayers--;
        deleteMatchesOfPlayer(playerId); // Delete the matches
    }

    // Desc.: Recursive implementation of deletePlayer(String playerId)
    private TennisPlayerContainerNode deletePlayerRec(TennisPlayerContainerNode currNode, String playerId) {
        // Checks if currNode is null
        if (currNode == null) {
            return currNode;
        }

        // Finds the node to delete
        if (playerId.compareTo(currNode.getPlayer().getId()) < 0) {
            currNode.setLeftChild(deletePlayerRec(currNode.getLeftChild(), playerId));
            return currNode;
        } else if (playerId.compareTo(currNode.getPlayer().getId()) > 0) {
            currNode.setRightChild(deletePlayerRec(currNode.getRightChild(), playerId));
            return currNode;
        } else {
            // Node has been found
            if (currNode.getLeftChild() == null) {
                if (currNode.getRightChild() == null) {
                    return null; // currNode is a leaf
                } else {
                    return currNode.getRightChild(); // currNode only has the right child
                }
            } else if (currNode.getRightChild() == null) {
                return currNode.getLeftChild(); // currNode only has the left child
            } else {
                TennisPlayerContainerNode tempPlayerNode = findLeftMost(currNode.getRightChild());
                TennisPlayerContainerNode tempRight = deleteLeftMost(currNode.getRightChild());
                currNode.setMatchList(tempPlayerNode.getMatchList());
                currNode.setPlayer(tempPlayerNode.getPlayer());
                currNode.setRightChild(tempRight);
                return currNode;
            }
        }
    }

    // Desc.: Finds the leftmost node (for deleteNode)
    // Output: Returns the node that is the leftmost descendant of the subtree
    // rooted at treeNode
    // NOTE: Based on the methods made by Giuseppe Turini
    private TennisPlayerContainerNode findLeftMost(TennisPlayerContainerNode currNode) {
        if (currNode.getLeftChild() == null) {
            return currNode;
        } else {
            return findLeftMost(currNode.getLeftChild());
        }
    }

    // Desc.: Deletes the leftmost descendant of treeNode
    // Output: Returns subtree of deleted node
    // NOTE: Based on the methods made by Giuseppe Turini
    private TennisPlayerContainerNode deleteLeftMost(TennisPlayerContainerNode currNode) {
        if (currNode.getLeftChild() == null) {
            return currNode.getRightChild();
        } else {
            TennisPlayerContainerNode replacementLeftChild = deleteLeftMost(currNode.getLeftChild());
            currNode.setLeftChild(replacementLeftChild);
            return currNode;
        }
    }

    // Desc.: Insert a tennis match into the lists of both tennis players of the
    // input match.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the insertion is not fully
    // successful.
    public void insertMatch(TennisMatch m) throws TennisDatabaseException {
        String player1Id = m.getIdPlayer1();
        String player2Id = m.getIdPlayer2();
        TennisPlayerContainerNode player1Node = getPlayerNode(player1Id);
        TennisPlayerContainerNode player2Node = getPlayerNode(player2Id);

        player1Node.insertMatch(m); // Inserts match into player 1 node
        player2Node.insertMatch(m); // Inserts match into player 2 node

        // NOTE: The insertMatch(TennisMatch m) will throw the error for us, so we do
        // not need to do it here

        if (m.getWinner() == 1) {
            // Add win to player 1 and loss to player 2
            TennisPlayer player1 = player1Node.getPlayer();
            player1.setWin(player1.getNumWin() + 1);
            TennisPlayer player2 = player2Node.getPlayer();
            player2.setLoss(player2.getNumLoss() + 1);
        } else {
            // Add win to player 2 and loss to player 1
            TennisPlayer player1 = player1Node.getPlayer();
            player1.setLoss(player1.getNumLoss() + 1);
            TennisPlayer player2 = player2Node.getPlayer();
            player2.setWin(player2.getNumWin() + 1);
        }

    }

    // Desc.: Returns all players in the database arranged in the output array
    // (sorted by id, alphabetically).
    // Output: Throws an unchecked (non-critical) exception if there are no players
    // in this container.
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        TennisPlayer[] players = getAllPlayersRec(this.root);

        // Check if players is empty
        if (players.length == 0) {
            throw new TennisDatabaseRuntimeException("TennisPlayerContainer: No players in this container");
        } else {
            return players;
        }
    }

    // Desc.: Recursive implementation of getAllPlayers()
    private TennisPlayer[] getAllPlayersRec(TennisPlayerContainerNode currNode) {
        if (currNode == null) {
            return new TennisPlayer[0];
        }

        TennisPlayer[] leftPlayers = getAllPlayersRec(currNode.getLeftChild());
        TennisPlayer[] rightPlayers = getAllPlayersRec(currNode.getRightChild());

        TennisPlayer[] combinedPlayers = new TennisPlayer[leftPlayers.length + rightPlayers.length + 1];

        int index = 0;
        for (TennisPlayer player : leftPlayers) {
            combinedPlayers[index++] = player;
        }

        combinedPlayers[index++] = currNode.getPlayer();

        for (TennisPlayer player : rightPlayers) {
            combinedPlayers[index++] = player;
        }

        return combinedPlayers;
    }

    // Desc.: Returns copies (deep copies) of all matches of input player (id)
    // arranged in the output array (sorted by date, most recent first).
    // Input: The id of a player.
    // Output: Throws a checked (critical) exception if the player (id) does not
    // exists.
    // Throws an unchecked (non-critical) exception if there are no matches (but the
    // player id exists).
    public TennisMatch[] getMatchesOfPlayer(String playerId)
            throws TennisDatabaseException, TennisDatabaseRuntimeException {
        return getPlayerNode(playerId).getMatches();
    }

    // Desc.: Deletes all matches of input player (id) from this container.
    // Input: The id of the tennis player.
    // Output: Throws an unchecked (non-critical) exception if no matches are
    // deleted.
    public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        boolean result = deleteMatchesOfPlayerRec(this.root, playerId);

        // Checks if a match was deleted
        if (!result) {
            throw new TennisDatabaseRuntimeException("TennisPlayerContainer: No match was deleted");
        }
    }

    // Desc.: Recursive implementation of deleteMatchesOfPlayer()
    // Input: The node we are currently checking and input player id
    // Output: Returns false if no matches were removed and true otherwise
    private boolean deleteMatchesOfPlayerRec(TennisPlayerContainerNode currNode, String playerId) {
        // Checks if currNode is null
        if (currNode == null) {
            return false;
        }

        boolean currResult = false; // Tells me whether or not any matches were deleted

        try {
            currNode.deleteMatchesOfPlayer(playerId);
            currResult = true; // If this runs, then a match was deleted
        } catch (TennisDatabaseRuntimeException e) {
            currResult = false;
        }
        boolean leftResult = deleteMatchesOfPlayerRec(currNode.getLeftChild(), playerId);
        boolean rightResult = deleteMatchesOfPlayerRec(currNode.getRightChild(), playerId);

        // If there is any true, it will "bubble up"
        return currResult || leftResult || rightResult;
    }

}