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

package dev.cubxity.plugins.metrics.mindustry.metric.server

import dev.cubxity.plugins.metrics.api.metric.collector.Collector
import dev.cubxity.plugins.metrics.api.metric.data.GaugeMetric
import dev.cubxity.plugins.metrics.api.metric.data.Metric
import fr.redstonneur1256.modlib.net.NetworkDebuggable
import mindustry.Vars
import mindustry.gen.Groups

class ServerCollector : Collector {
    override fun collect(): List<Metric> {
        val samples: MutableList<Metric> = ArrayList()
        samples.add(GaugeMetric("mindustry_player_limit", value = Vars.netServer.admins.playerLimit))
        samples.add(GaugeMetric("mindustry_player_count", value = Groups.player.size()))
        samples.add(GaugeMetric("mindustry_mod_count", value = Vars.mods.list().size))

        if (!Groups.player.isEmpty) {
            var pingMin = Long.MAX_VALUE
            var pingMax = Long.MIN_VALUE
            var totalPing: Long = 0
            for (player in Groups.player) {
                val ping = (player as NetworkDebuggable).ping
                if (ping <= 0) {
                    continue
                }
                pingMin = pingMin.coerceAtMost(ping)
                pingMax = pingMax.coerceAtLeast(ping)
                totalPing += ping
            }
            if (totalPing != 0L) {
                val averagePing = totalPing / Groups.player.size()
                samples.add(GaugeMetric("mindustry_player_ping_min", value = pingMin))
                samples.add(GaugeMetric("mindustry_player_ping_max", value = pingMax))
                samples.add(GaugeMetric("mindustry_player_ping_avg", value = averagePing))
            }
        }

        return samples
    }
}