package io.example.logic;

import io.example.infrastructure.BaseVerticle;
import io.example.infrastructure.SpringVerticle;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@SpringVerticle(worker = true)
public class DataWriter extends BaseVerticle {
}
