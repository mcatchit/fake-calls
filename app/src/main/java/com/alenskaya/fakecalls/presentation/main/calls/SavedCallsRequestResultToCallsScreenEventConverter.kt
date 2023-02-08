package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts saved calls request result to CallsScreen ui event
 */
object SavedCallsRequestResultToCallsScreenEventConverter :
    Converter<BaseResponse<List<SavedCall>, DatabaseError>, CallsScreenEvent> {

    override fun convert(input: BaseResponse<List<SavedCall>, DatabaseError>): CallsScreenEvent {
        return when (input) {
            is BaseResponse.Success -> input.payload.toHomeScreenEvent()
            is BaseResponse.Error -> CallsScreenEvent.CallsNotLoaded
        }
    }

    private fun List<SavedCall>.toHomeScreenEvent(): CallsScreenEvent {
        return if (isNotEmpty()) {
            CallsScreenEvent.CallsLoaded(
                scheduledCalls = takeWithStatus(CallStatus.SCHEDULED).sortByDate(true)
                    .convertToModel(),
                completedCalls = takeWithStatus(CallStatus.COMPLETED).sortByDate(false)
                    .convertToModel()
            )
        } else {
            CallsScreenEvent.CallsLoadedEmpty
        }
    }

    private fun List<SavedCall>.takeWithStatus(status: CallStatus) = filter { call ->
        call.callStatus == status
    }

    private fun List<SavedCall>.sortByDate(ascending: Boolean) = if (ascending) sortedBy {
        it.date
    } else sortedByDescending {
        it.date
    }

    private fun List<SavedCall>.convertToModel() = map { savedCall ->
        SavedCallToCallModelConverter.convert(savedCall)
    }
}
