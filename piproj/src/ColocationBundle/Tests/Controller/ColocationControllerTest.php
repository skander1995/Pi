<?php

namespace ColocationBundle\Tests\Controller;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class ColocationControllerTest extends WebTestCase
{
    public function testCreate()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/colocation/');
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr'));
        $crawler = $client->click($crawler->filter('.new_entry a')->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'colocation[idUsr]' => 42,
            'colocation[datepub]' => new \DateTime(),
            'colocation[description]' => 'Lorem ipsum dolor sit amet',
            'colocation[etat]' => 'Lorem ipsum dolor sit amet',
            'colocation[lieu]' => 'Lorem ipsum dolor sit amet',
            'colocation[loyermensuel]' => 'Lorem ipsum dolor sit amet',
            'colocation[typelogement]' => 'Lorem ipsum dolor sit amet',
            'colocation[typechambre]' => 'Lorem ipsum dolor sit amet',
            'colocation[imgcouverture]' => 'Lorem ipsum dolor sit amet',
            'colocation[nbchambre]' => 42,
            'colocation[commodite]' => 'Lorem ipsum dolor sit amet',
            'colocation[datedisponibilite]' => new \DateTime(),
                    ));
        $client->submit($form);
        $crawler = $client->followRedirect();
        $crawler = $client->click($crawler->filter('.record_actions a')->link());
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr'));
    }

    public function testCreateError()
    {
        $client = static::createClient();
        $crawler = $client->request('GET', '/admin/colocation/new');
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
        $crawler = $client->request('GET', '/admin/colocation/');
        $this->assertCount(1, $crawler->filter('table.records_list tbody tr:contains("First value")'));
        $this->assertCount(0, $crawler->filter('table.records_list tbody tr:contains("Changed")'));
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'colocation[idUsr]' => 42,
            'colocation[datepub]' => new \DateTime(),
            'colocation[description]' => 'Changed',
            'colocation[etat]' => 'Changed',
            'colocation[lieu]' => 'Changed',
            'colocation[loyermensuel]' => 'Changed',
            'colocation[typelogement]' => 'Changed',
            'colocation[typechambre]' => 'Changed',
            'colocation[imgcouverture]' => 'Changed',
            'colocation[nbchambre]' => 42,
            'colocation[commodite]' => 'Changed',
            'colocation[datedisponibilite]' => new \DateTime(),
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
        $crawler = $client->request('GET', '/admin/colocation/');
        $crawler = $client->click($crawler->filter('table.records_list tbody tr td .btn-group a')->eq(1)->link());
        $form = $crawler->filter('form button[type="submit"]')->form(array(
            'colocation[field_name]' => '',
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
        $crawler = $client->request('GET', '/admin/colocation/');
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
        $crawler = $client->request('GET', '/admin/colocation/');
        $form = $crawler->filter('div#filter form button[type="submit"]')->form(array(
            'colocation_filter[idUsr]' => 42,
            'colocation_filter[datepub]' => new \DateTime(),
            'colocation_filter[description]' => 'First%',
            'colocation_filter[etat]' => 'First%',
            'colocation_filter[lieu]' => 'First%',
            'colocation_filter[loyermensuel]' => 'First%',
            'colocation_filter[typelogement]' => 'First%',
            'colocation_filter[typechambre]' => 'First%',
            'colocation_filter[imgcouverture]' => 'First%',
            'colocation_filter[nbchambre]' => 42,
            'colocation_filter[commodite]' => 'First%',
            'colocation_filter[datedisponibilite]' => new \DateTime(),
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
        $crawler = $client->request('GET', '/admin/colocation/');
        $this->assertCount(1, $crawler->filter('table.records_list th')->eq(0)->filter('a i.fa-sort'));
        $crawler = $client->click($crawler->filter('table.records_list th a')->link());
        $crawler = $client->followRedirect();
        $this->assertTrue($client->getResponse()->isSuccessful());
        $this->assertCount(1, $crawler->filter('table.records_list th a i.fa-sort-up'));
    }
}
