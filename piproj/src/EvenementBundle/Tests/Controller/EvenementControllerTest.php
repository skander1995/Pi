<?php

namespace EvenementBundle\Tests\Controller;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class EvenementControllerTest extends WebTestCase
{
    public function testCreate()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/evenement/');
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr'));
        $crawler = $client->click($crawler->filter('.new_entry a')->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'evenement[idUsr]' => 42,
            'evenement[datepub]' => new \DateTime(),
            'evenement[description]' => 'Lorem ipsum dolor sit amet',
            'evenement[etat]' => 'Lorem ipsum dolor sit amet',
            'evenement[nomEvent]' => 'Lorem ipsum dolor sit amet',
            'evenement[datedebut]' => new \DateTime(),
            'evenement[datefin]' => new \DateTime(),
            'evenement[lieu]' => 'Lorem ipsum dolor sit amet',
            'evenement[affiche]' => 'Lorem ipsum dolor sit amet',
            'evenement[placeDispo]' => 42,
                    ));
        $client->submit($form);
        $crawler = $client->followRedirect();
        $crawler = $client->click($crawler->filter('.record_actions a')->link());
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr'));
    }

    public function testCreateError()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/evenement/new');
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
        $crawler = $client->request('GET', '/admin/evenement/');
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr:contains("First value")'));
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr:contains("Changed")'));
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'evenement[idUsr]' => 42,
            'evenement[datepub]' => new \DateTime(),
            'evenement[description]' => 'Changed',
            'evenement[etat]' => 'Changed',
            'evenement[nomEvent]' => 'Changed',
            'evenement[datedebut]' => new \DateTime(),
            'evenement[datefin]' => new \DateTime(),
            'evenement[lieu]' => 'Changed',
            'evenement[affiche]' => 'Changed',
            'evenement[placeDispo]' => 42,
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
        $crawler = $client->request('GET', '/admin/evenement/');
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'evenement[field_name]' => '',
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
        $crawler = $client->request('GET', '/admin/evenement/');
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
        $crawler = $client->request('GET', '/admin/evenement/');
        $form = $crawler->filter('div#filter form button[type="submit"]')->form(array(
            'evenement_filter[idUsr]' => 42,
            'evenement_filter[datepub]' => new \DateTime(),
            'evenement_filter[description]' => 'First%',
            'evenement_filter[etat]' => 'First%',
            'evenement_filter[nomEvent]' => 'First%',
            'evenement_filter[datedebut]' => new \DateTime(),
            'evenement_filter[datefin]' => new \DateTime(),
            'evenement_filter[lieu]' => 'First%',
            'evenement_filter[affiche]' => 'First%',
            'evenement_filter[placeDispo]' => 42,
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
        $crawler = $client->request('GET', '/admin/evenement/');
        $this->assertCount(1, $crawler->filter('table.records_list th')->eq(0)->filter('a i.fa-sort'));
        $crawler = $client->click($crawler->filter('table.records_list th a')->link());
        $crawler = $client->followRedirect();
        $this->assertTrue($client->getResponse()->isSuccessful());
        $this->assertCount(1, $crawler->filter('table.records_list th a i.fa-sort-up'));
    }
}
