package com.belstu.thesisproject.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignalMessage {

  private String type;
  private String sender;
  private String receiver;
  private Object data;
}
