/*
 * Copyright (c) 2022, 2024 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * OpenAPI Helidon Quickstart
 * This is a sample for Helidon Quickstart project.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.openapitools.server.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
  * An message for the user
 **/

public class Message  {
  
  private String message;

  private String greeting;

 /**
   * Get message
   * @return message
  **/
  public String getMessage() {
    return message;
  }

  /**
    * Set message
  **/
  public void setMessage(String message) {
    this.message = message;
  }

  public Message message(String message) {
    this.message = message;
    return this;
  }

 /**
   * Get greeting
   * @return greeting
  **/
  public String getGreeting() {
    return greeting;
  }

  /**
    * Set greeting
  **/
  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

  public Message greeting(String greeting) {
    this.greeting = greeting;
    return this;
  }


  /**
    * Create a string representation of this pojo.
  **/
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    greeting: ").append(toIndentedString(greeting)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

