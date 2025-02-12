package org.anaservinovska.travelplatform.dto;

import lombok.*;
import org.anaservinovska.travelplatform.models.UserSubscriptionType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSubscriptionRequest {
    private UserSubscriptionType subscriptionType;
}
