package com.xiaomi.infra.galaxy.fds.result;

import java.util.LinkedList;
import java.util.List;

public class ListInventoryConfigurationResult {

  private List<InventoryConfiguration> inventoryConfigurations = new LinkedList<InventoryConfiguration>();

  private boolean isTruncated;

  private String continuationToken;

  private String nextContinuationToken;

  public List<InventoryConfiguration> getInventoryConfigurations() {
    return inventoryConfigurations;
  }

  public void setInventoryConfigurations(List<InventoryConfiguration> inventoryConfigurations) {
    this.inventoryConfigurations = inventoryConfigurations;
  }

  public boolean isTruncated() {
    return isTruncated;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public String getContinuationToken() {
    return continuationToken;
  }

  public void setContinuationToken(String continuationToken) {
    this.continuationToken = continuationToken;
  }

  public String getNextContinuationToken() {
    return nextContinuationToken;
  }

  public void setNextContinuationToken(String nextContinuationToken) {
    this.nextContinuationToken = nextContinuationToken;
  }

  public void addInventoryConfigurations(InventoryConfiguration inventoryConfiguration) {
    if (inventoryConfigurations == null) {
      inventoryConfigurations = new LinkedList<InventoryConfiguration>();
    }
    inventoryConfigurations.add(inventoryConfiguration);
  }

  @Override
  public String toString() {
    return "ListInventoryConfigurationResult{" + "inventoryConfigurations="
        + inventoryConfigurations + ", isTruncated=" + isTruncated + ", continuationToken='"
        + continuationToken + '\'' + ", nextContinuationToken='" + nextContinuationToken + '\''
        + '}';
  }
}
