package com.revature.presentation;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.service.Service;

public class PresentatioImplTest {

	PresentationImpl pres;
	
	@Mock
	Service fakeService;
	
	@Before
	public void setupService() {
		fakeService = mock(Service.class);
		pres = new PresentationImpl(fakeService);
	}
	
	@Test
	public void testAdd() {
		
	}
}
