package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.context.WebApplicationContext;

import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = ReglementJUnitTest.class)
@ActiveProfiles("test")

@Slf4j
public class ReglementJUnitTest {
	
	
	 @Mock
	    ReglementRepository reglementRepository;
	    @InjectMocks
	    ReglementServiceImpl reglementService;
	    private WebApplicationContext webApplicationContext;

	    Date dateReglement = new Date("12/12/2002");
	    Date dateCreationFacture = new Date("12/12/2002");
	    Date dateDerniereModificationFacture = new Date("12/12/2012");
	    Facture f = new Facture();
	    // cree un reglement pour tester
	    Reglement reglement = new Reglement((long) 15,1200,1000,true,dateReglement,f);
	    List<Reglement> reglementList = Arrays.asList(reglement);


	    @Test
	    @Order(1)
	    void  testRetrieveAllReglements() throws Exception {
	        log.debug("Tester Retrive All reglements");
	        when(reglementRepository.findAll()).thenReturn(reglementList);
	        List<Reglement> reglementList = reglementService.retrieveAllReglements();
	        Assertions.assertNotNull(reglementList);

	    }

	    @Test
	    @Order(2)
	    void testAddreglement() {
	        log.debug("Tester Ajout du reglement");
	        when(reglementRepository.save(ArgumentMatchers.any(Reglement.class))).thenReturn(reglement);

	        // utiliser la methode dans le service
	        Reglement created =reglementService.addReglement(reglement);
	        // verifier que reglement existe
	        //Assertions.assertNotNull(reglementService.retrievereglement(1L));
	        Assertions.assertEquals(created.getDateReglement(),(reglement.getDateReglement()));
	    }


	  /*  @Test
	    @Order(3)
	    void testgetChiffreAffaireEntreDeuxDate() {
	        Date date1 = new Date("12/12/2000");
	  		    Date date2 = new Date("12/12/2002");
	        log.debug("Tester get Chiffre Affaire Entre Deux Dates");
//	        when(reglementRepository.getChiffreAffaireEntreDeuxDate(date1, date2).thenReturn(Optional.of((0.5)));
	  	        float ch =reglementService.getChiffreAffaireEntreDeuxDate(date1, date2);
	        // verifier que le chiffre d'affaire existe
	        Assertions.assertNotNull(ch);
	        
	    }*/

	    @Test
	    @Order(4)
	    void testRetrievereglement() {


	        log.debug("Tester retrive du reglement");
	        when(reglementRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(reglement));
	        // utiliser la methode dans le service
	        Reglement reglementretrived = reglementService.retrieveReglement(1L);
	        // verifier que reglement existe
	        Assertions.assertNotNull(reglementretrived);

	    }
	
	
}
