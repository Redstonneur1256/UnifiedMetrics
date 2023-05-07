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

package dev.cubxity.plugins.metrics.mindustry.metric.world

import dev.cubxity.plugins.metrics.api.metric.collector.Collector
import dev.cubxity.plugins.metrics.api.metric.data.GaugeMetric
import dev.cubxity.plugins.metrics.api.metric.data.Metric
import mindustry.Vars
import mindustry.gen.Groups

class WorldCollector : Collector {

    override fun collect(): List<Metric> {
        return listOf(
            GaugeMetric("mindustry_unit_count", value = Groups.unit.size()),
            GaugeMetric("mindustry_build_count", value = Groups.build.size()),
            GaugeMetric("mindustry_bullet_count", value = Groups.bullet.size()),
            GaugeMetric("mindustry_fire_count", value = Groups.fire.size()),
            GaugeMetric("mindustry_wave", value = Vars.state.wave)
        )
    }
    
}
