/*
 * MIT License
 *
 * Copyright (c) 2022 Catch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Catch
 * @since 2022-03-25
 */
public class IdStrategyFactory {

    private static final Map<IdStrategy.Type, IdStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(IdStrategy.Type.UUID, new UuidStrategy());
        strategyMap.put(IdStrategy.Type.SNOW_FLAKE, new SnowFlakeStrategy());
    }

    public IdStrategy get(IdStrategy.Type type) {
        return strategyMap.get(type);
    }

}