package test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TowerTest {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(3);
    }

    @Test
    public void testInitializationWithValidDisks() {
        assertEquals(3, tower.getNumberOfDisks());
    }

    @Test
    public void testInitializationWithZeroDisks() {
        tower = new Tower(0);
        assertEquals(0, tower.getNumberOfDisks());
    }

    @Test
    public void testMoveValidDiskToEmptyTower() {
        assertTrue(tower.moveDisk(0, 1));
        assertEquals(2, tower.getNumberOfDisks());
    }

    @Test
    public void testMoveInvalidDiskOverSmallerDisk() {
        assertFalse(tower.moveDisk(0, 1));
        assertEquals(3, tower.getNumberOfDisks());
    }

    @Test
    public void testMoveDiskOutsideTowerLimits() {
        assertFalse(tower.moveDisk(0, 3));
        assertFalse(tower.moveDisk(1, -1));
        assertEquals(3, tower.getNumberOfDisks());
    }

    @Test
    public void testCheckWinAfterMovingAllDisksToLastTower() {
        tower.moveDisk(0, 2);
        tower.moveDisk(0, 1);
        tower.moveDisk(2, 1);
        tower.moveDisk(0, 2);
        tower.moveDisk(1, 0);
        tower.moveDisk(1, 2);
        tower.moveDisk(0, 2);
        assertTrue(tower.checkWin());
    }

    private class Tower {

        private int[] disks;

        public Tower(int numberOfDisks) {
            disks = new int[numberOfDisks];
            for (int i = 0; i < numberOfDisks; i++) {
                disks[i] = i;
            }
        }

        public int getNumberOfDisks() {
            return disks.length;
        }

        public boolean moveDisk(int fromTower, int toTower) {
            if (fromTower == toTower) {
                return false;
            }

            if (disks[fromTower - 1] > disks[toTower - 1]) {
                return false;
            }

            int temp = disks[fromTower - 1];
            disks[fromTower - 1] = disks[toTower - 1];
            disks[toTower - 1] = temp;
            return true;
        }

        public boolean checkWin() {
            return disks.length == 0;
        }
    }
}