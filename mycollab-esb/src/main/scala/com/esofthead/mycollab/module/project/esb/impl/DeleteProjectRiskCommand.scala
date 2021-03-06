/**
 * This file is part of mycollab-esb.
 *
 * mycollab-esb is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-esb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-esb.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.esb.impl

import com.esofthead.mycollab.common.dao.{CommentMapper, TagMapper}
import com.esofthead.mycollab.common.domain.{CommentExample, TagExample}
import com.esofthead.mycollab.module.GenericCommand
import com.esofthead.mycollab.module.ecm.service.ResourceService
import com.esofthead.mycollab.module.file.AttachmentUtils
import com.esofthead.mycollab.module.project.ProjectTypeConstants
import com.esofthead.mycollab.module.project.esb.DeleteProjectRiskEvent
import com.google.common.eventbus.{AllowConcurrentEvents, Subscribe}
import org.springframework.beans.factory.annotation.Autowired

/**
  * @author MyCollab Ltd
  * @since 5.2.9
  */
class DeleteProjectRiskCommand extends GenericCommand {
  @Autowired private val resourceService: ResourceService = null
  @Autowired private val commentMapper: CommentMapper = null
  @Autowired private val tagMapper: TagMapper = null

  @AllowConcurrentEvents
  @Subscribe
  def removeRisk(event: DeleteProjectRiskEvent): Unit = {
    for (risk <- event.risks) {
      removeRelatedFiles(event.accountId, risk.getProjectid, risk.getId)
      removeRelatedComments(risk.getId)
      removeRelatedTags(risk.getId)
    }
  }

  private def removeRelatedFiles(accountId: Integer, projectId: Integer, riskId: Integer) {
    val attachmentPath = AttachmentUtils.getProjectEntityAttachmentPath(accountId, projectId,
      ProjectTypeConstants.RISK, "" + riskId)
    resourceService.removeResource(attachmentPath, "", accountId)
  }

  private def removeRelatedComments(riskId: Integer) {
    val ex = new CommentExample
    ex.createCriteria.andTypeEqualTo(ProjectTypeConstants.RISK).andExtratypeidEqualTo(riskId)
    commentMapper.deleteByExample(ex)
  }

  private def removeRelatedTags(riskId: Integer): Unit = {
    val ex = new TagExample
    ex.createCriteria().andTypeEqualTo(ProjectTypeConstants.RISK).andTypeidEqualTo(riskId + "")
    tagMapper.deleteByExample(ex)
  }
}
