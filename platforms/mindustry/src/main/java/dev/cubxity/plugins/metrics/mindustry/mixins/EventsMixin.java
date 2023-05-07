/*
 *     This file is part of UnifiedMetrics.
 *
 *     UnifiedMetrics is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     UnifiedMetrics is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with UnifiedMetrics.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cubxity.plugins.metrics.mindustry.mixins;

import arc.Events;
import arc.struct.ObjectMap;
import dev.cubxity.plugins.metrics.mindustry.metric.event.EventCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Events.class)
public class EventsMixin {

    @Redirect(method = { "fire(Ljava/lang/Enum;)V", "fire(Ljava/lang/Class;Ljava/lang/Object;)V" },
            at = @At(value = "INVOKE", target = "Larc/struct/ObjectMap;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K> Object fire1(ObjectMap<Object, ?> instance, Object key) {
        EventCollection.INSTANCE.increment(key);
        return instance.get(key);
    }

}
