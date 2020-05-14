package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init() {
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);

    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    this.ship = new GT4500(primary, secondary);
  }

  /// Original
  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  /// Original
  @Test
  public void fireTorpedo_All_Success() {
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  /// Specification-based
  @Test
  public void fireTorpedo_Single_PrimaryFirst() {
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  /// Specification-based
  @Test
  public void fireTorpedo_Single_SecondarySecond() {
    // Arrange

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1 && result2);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  /// Mixed
  @Test
  public void fireTorpedo_Single_AdaptToEmptySecondary() {
    // Arrange
    when(secondary.fire(1)).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1 && result2);
  }

  /// Mixed
  @Test
  public void fireTorpedo_Single_AdaptToEmptyPrimary() {
    // Arrange
    when(primary.fire(1)).thenReturn(false);
    when(primary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1 && result2);
  }

  /// Structural
  @Test
  public void fparameterlessConstructor() {
    // Arrange
    ship = new GT4500();

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1 && result2);
  }
}
