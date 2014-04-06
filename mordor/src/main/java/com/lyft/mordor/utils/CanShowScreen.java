package com.lyft.mordor.utils;

import flow.Flow;
import mortar.Blueprint;

public interface CanShowScreen<S extends Blueprint> {
  void showScreen(S screen, Flow.Direction direction);
}
