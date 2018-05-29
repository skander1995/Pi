<?php

namespace CovoiturageBundle\Tests\Controller;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class CovoiturageControllerTest extends WebTestCase
{
    public function testCreate()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr'));
        $crawler = $client->click($crawler->filter('.new_entry a')->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'covoiturage[idUsr]' => 42,
            'covoiturage[datepub]' => new \DateTime(),
            'covoiturage[description]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[etat]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[lieudepart]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[lieuarrive]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[datedepart]' => new \DateTime(),
            'covoiturage[prix]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[checkpoints]' => 'Lorem ipsum dolor sit amet',
            'covoiturage[nbplace]' => 42,
                    ));
        $client->submit($form);
        $crawler = $client->followRedirect();
        $crawler = $client->click($crawler->filter('.record_actions a')->link());
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr'));
    }

    public function testCreateError()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/new');
        $form = $crawler->filter('form button[type="submit"]')->form();
        $crawler = $client->submit($form);
        $this->assertGreaterThan(0, $crawler->filter('form div.has-error')->count());
        $this->assertTrue($client->getResponse()->isSuccessful());
    }

    /**
     * @depends testCreate
     */
    public function testEdit()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr:contains("First value")'));
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr:contains("Changed")'));
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'covoiturage[idUsr]' => 42,
            'covoiturage[datepub]' => new \DateTime(),
            'covoiturage[description]' => 'Changed',
            'covoiturage[etat]' => 'Changed',
            'covoiturage[lieudepart]' => 'Changed',
            'covoiturage[lieuarrive]' => 'Changed',
            'covoiturage[datedepart]' => new \DateTime(),
            'covoiturage[prix]' => 'Changed',
            'covoiturage[checkpoints]' => 'Changed',
            'covoiturage[nbplace]' => 42,
            // ... adapt fields value here ...
        ));
        $client->submit($form);
        $crawler = $client->followRedirect();
        $crawler = $client->click($crawler->filter('.record_actions a')->link());
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr:contains("First value")'));
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr:contains("Changed")'));
    }

    /**
     * @depends testCreate
     */
    public function testEditError()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'covoiturage[field_name]' => '',
            // ... use a required field here ...
        ));
        $crawler = $client->submit($form);
        $this->assertGreaterThan(0, $crawler->filter('form div.has-error')->count());
    }

    /**
     * @depends testCreate
     */
    public function testDelete()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $this->assertTrue($client->getResponse()->isSuccessful());
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr'));
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(0)->link());
        $client->submit($crawler->filter('form#delete button[type="submit"]')->form());
        $crawler = $client->followRedirect();
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr'));
    }

    /**
     * @depends testCreate
     */
    public function testFilter()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $form = $crawler->filter('div#filter form button[type="submit"]')->form(array(
            'covoiturage_filter[idUsr]' => 42,
            'covoiturage_filter[datepub]' => new \DateTime(),
            'covoiturage_filter[description]' => 'First%',
            'covoiturage_filter[etat]' => 'First%',
            'covoiturage_filter[lieudepart]' => 'First%',
            'covoiturage_filter[lieuarrive]' => 'First%',
            'covoiturage_filter[datedepart]' => new \DateTime(),
            'covoiturage_filter[prix]' => 'First%',
            'covoiturage_filter[checkpoints]' => 'First%',
            'covoiturage_filter[nbplace]' => 42,
            // ... maybe use just one field here ...
        ));
        $client->submit($form);
        $crawler = $client->followRedirect();
        $this->assertTrue($client->getResponse()->isSuccessful());
        $crawler = $client->click($crawler->filter('div#filter a')->link());
        $crawler = $client->followRedirect();
        $this->assertTrue($client->getResponse()->isSuccessful());
    }    /**
     * @depends testCreate
     */
    public function testSort()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/covoiturage/');
        $this->assertCount(1, $crawler->filter('table.records_list th')->eq(0)->filter('a i.fa-sort'));
        $crawler = $client->click($crawler->filter('table.records_list th a')->link());
        $crawler = $client->followRedirect();
        $this->assertTrue($client->getResponse()->isSuccessful());
        $this->assertCount(1, $crawler->filter('table.records_list th a i.fa-sort-up'));
    }
}
