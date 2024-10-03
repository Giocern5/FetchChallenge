package com.example.fetchchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.fetchchallenge.data.User
import com.example.fetchchallenge.data.repository.UserRepository
import com.example.fetchchallenge.features.userfeed.UserFeedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import com.example.fetchchallenge.data.Result
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.mockito.Mockito.verify


@ExperimentalCoroutinesApi
class UserFeedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var viewModel: UserFeedViewModel

    @Mock
    private lateinit var observer: Observer<Map<Int, List<User>>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = UserFeedViewModel(repository)
        viewModel.userFeedData.observeForever(observer)
    }

    @Test
    fun `setData should post value on success`() = runTest {
        // Given
        val storeResponseList = listOf(
            User(id = 123, name = "Item 123", listId = 1),
            User(id = 456, name = "Item 456", listId = 1)
        )

        val expectedGroupedData = mapOf(
            1 to storeResponseList.sortedBy { it.id }  // Sort users by id in the list
        )

        // Mock the repository to return the given response
        `when`(repository.getUsers()).thenReturn(Result.Success(storeResponseList))

        // Assert initial state
        assertNull(viewModel.userFeedData.value)

        viewModel.fetchUsers()

        // Wait until all coroutines have completed
        advanceUntilIdle()

        // Verify the observer received the expected data
        verify(observer).onChanged(expectedGroupedData)
        assertEquals(expectedGroupedData, viewModel.userFeedData.value)
    }
}