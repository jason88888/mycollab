/**
 * This file is part of mycollab-localization.
 *
 * mycollab-localization is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-localization is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-localization.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.i18n;

import ch.qos.cal10n.BaseName;
import com.esofthead.mycollab.core.MyCollabException;

/**
 * @author MyCollab Ltd.
 * @since 4.3.3
 */
public class OptionI18nEnum {
    public static TaskPriority[] task_priorities = {TaskPriority.Urgent,
            TaskPriority.High, TaskPriority.Medium, TaskPriority.Low,
            TaskPriority.None};

    public static BugStatus[] bug_statuses = {BugStatus.Open,
            BugStatus.Verified, BugStatus.Resolved,
            BugStatus.ReOpen};

    public static BugPriority[] bug_priorities = {BugPriority.Blocker,
            BugPriority.Critical, BugPriority.Major, BugPriority.Minor,
            BugPriority.Trivial};

    public static BugSeverity[] bug_severities = {BugSeverity.Critical,
            BugSeverity.Major, BugSeverity.Minor, BugSeverity.Trivial};

    public static BugResolution[] bug_resolutions = {BugResolution.Fixed,
            BugResolution.Won_Fix, BugResolution.Duplicate,
            BugResolution.Invalid, BugResolution.CannotReproduce, BugResolution.InComplete};

    public static InvoiceStatus[] invoiceStatuses = {InvoiceStatus.Paid,
            InvoiceStatus.Sent, InvoiceStatus.Scheduled};

    @BaseName("project-milestone-status")
    public enum MilestoneStatus {
        Future, Closed, InProgress
    }

    @BaseName("project-invoice-status")
    public enum InvoiceStatus {
        Paid, Sent, Scheduled, All
    }

    @BaseName("project-task-priority")
    public enum TaskPriority {
        Urgent, High, Medium, Low, None
    }

    @BaseName("project-bug-status")
    public enum BugStatus {
        Open, Verified, Resolved, ReOpen
    }

    @BaseName("project-bug-priority")
    public enum BugPriority {
        Blocker, Critical, Major, Minor, Trivial
    }

    @BaseName("project-bug-severity")
    public enum BugSeverity {
        Critical, Major, Minor, Trivial
    }

    @BaseName("project-bug-resolution")
    public enum BugResolution {
        Fixed,
        Won_Fix,
        Duplicate,
        Invalid,
        CannotReproduce,
        InComplete,
        None
    }

    @BaseName("project-bug-related")
    public enum BugRelation {
        Related, Duplicated, Block, DependsOn, Duplicate, Relation;

        public Enum getReverse() {
            if (this == Duplicated) {
                return Duplicate;
            } else if (this == Related) {
                return Relation;
            } else if (this == Block) {
                return DependsOn;
            } else {
                throw new MyCollabException("Not support");
            }
        }
    }
}
