package tn.esprit.rh.achat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.SecteurActiviteServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SecteurActiviteServiceTest {

	@Mock
	SecteurActiviteRepository secteurActiviteRepo;
	@InjectMocks 
	SecteurActiviteServiceImpl secteurActiviteServiceImp;

	Set<Fournisseur> fournisseurs = new HashSet<Fournisseur>();
	
	SecteurActivite secteur;
	
	
	
	void testRetrieveAllSecteurActivite() {

		secteurActiviteServiceImp.retrieveAllSecteurActivite();
		verify(secteurActiviteRepo).findAll();
		
	}
	
	void initiateSect() {
		Fournisseur f1 = new Fournisseur();
		Fournisseur f2 = new Fournisseur();
		Fournisseur f3 = new Fournisseur();
		Fournisseur f4 = new Fournisseur();
		fournisseurs.add(f1);
		fournisseurs.add(f2);
		fournisseurs.add(f3);
		fournisseurs.add(f4);
		secteur = new SecteurActivite((long) 10, "code s1", "libelleSec1", fournisseurs);
	}
	
	@Test
	public void testRetrieveSecteurActivite() {

		this.initiateSect();
		Mockito.when(secteurActiviteRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(secteur));
		assertNotNull(secteurActiviteServiceImp.retrieveSecteurActivite((long)10));	
	}
	
	@Test
	public void testaddSecteurActivite() {
		this.initiateSect();

		Mockito.when(secteurActiviteRepo.save(Mockito.any(SecteurActivite.class))).thenReturn(secteur);
		assertNotNull(secteurActiviteServiceImp.addSecteurActivite(secteur));
	}
	
	@Test
	public void testUpdateSecteurActivite() {
		
		this.initiateSect();


		Mockito.when(secteurActiviteRepo.save(Mockito.any(SecteurActivite.class))).thenReturn(secteur);
		secteur.setLibelleSecteurActivite("Updated Libelle");
		assertNotNull(secteurActiviteServiceImp.updateSecteurActivite(secteur));	
		assertEquals("Updated Libelle", secteur.getLibelleSecteurActivite());
	}
	
	
	@Test
	public void testDeleteSecteurActivite() {
		secteurActiviteServiceImp.deleteSecteurActivite((long)10);
		verify(secteurActiviteRepo).deleteById((long)10);
	}
	
	
	
	
	
	
}
