/**
 * Copyright OPS4J
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.wicket.samples.departmentstore.service.basic.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.ops4j.pax.wicket.samples.departmentstore.model.DepartmentStore;
import org.ops4j.pax.wicket.samples.departmentstore.model.Floor;
import org.ops4j.pax.wicket.samples.departmentstore.model.Franchisee;

public class DepartmentStoreImpl implements DepartmentStore, Serializable {

    private static final long serialVersionUID = 1L;

    private List<Floor> floors;
    private String name;
    private String history;

    public DepartmentStoreImpl(String name) {
        this.name = name;

        floors = new ArrayList<Floor>();
        floors.add(newCFloor());
        floors.add(newLGFloor());
        floors.add(newGFloor());
        floors.add(newFirstFloor());
        floors.add(newSecondFloor());
        floors.add(newThirdFloor());
        floors.add(newForthFloor());
        floors.add(newFifthFloor());

        try {
            history = loadHistory();
        } catch (IOException e) {
            history = "Error reading history: " + e.getMessage();
        }
    }

    private Floor newFifthFloor() {
        return new FloorImpl("RoofTop");
    }

    private Floor newForthFloor() {
        Floor forthFloor = new FloorImpl("4th");

        Franchisee footCourt = new Franchisee("Hang Wah Seng", "More food");
        forthFloor.addFranchisee(footCourt);

        return forthFloor;
    }

    private Floor newThirdFloor() {
        Floor thirdFloor = new FloorImpl("3rd");

        Franchisee apple = new Franchisee("Apple Computers", "Fruity machines");
        thirdFloor.addFranchisee(apple);

        return thirdFloor;
    }

    private Floor newSecondFloor() {
        Floor secondFloor = new FloorImpl("2nd");

        Franchisee teppanyaki =
            new Franchisee("Teppanyaki", "Fried Food of Japan like the Japanese never tasted.");
        secondFloor.addFranchisee(teppanyaki);

        return secondFloor;
    }

    private Floor newFirstFloor() {
        Floor firstFloor = new FloorImpl("1st");

        Franchisee franchisee = new Franchisee("Esquire Kitchen", "Chinese Food");
        firstFloor.addFranchisee(franchisee);

        return firstFloor;
    }

    private Floor newGFloor() {
        Floor gFloor = new FloorImpl("G");

        Franchisee famouseAmos = new Franchisee("Famous Amos", "Who?");
        gFloor.addFranchisee(famouseAmos);

        Franchisee rejectShop = new Franchisee("RejectShop", "Reject the shop");
        gFloor.addFranchisee(rejectShop);

        Franchisee watsons = new Franchisee("Watson's", "Drugs and Rock'n Roll");
        gFloor.addFranchisee(watsons);

        return gFloor;
    }

    private Floor newLGFloor() {
        Floor floor = new FloorImpl("LG");

        Franchisee levi = new Franchisee("Levi", "Jeans");
        floor.addFranchisee(levi);

        Franchisee vinci = new Franchisee("Vinci", "Shoes, shoes, shoes...");
        floor.addFranchisee(vinci);

        return floor;
    }

    private Floor newCFloor() {
        Floor cFloor = new FloorImpl("C");

        Franchisee mcDonald = new Franchisee("McDonald", "Fast food, bad mouth feel.");
        cFloor.addFranchisee(mcDonald);

        Franchisee kfc = new Franchisee("KFC", "Fast Food, Licking your ....");
        cFloor.addFranchisee(kfc);

        return cFloor;
    }

    public String getName() {
        return name;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Franchisee> getAllFranchisees() {
        List<Franchisee> all = new ArrayList<Franchisee>();
        for (Floor floor : floors) {
            List<Franchisee> franchisees = floor.getFranchisees();
            all.addAll(franchisees);
        }
        return all;
    }

    public String getHistory() {
        return history;
    }

    private String loadHistory() throws IOException {
        InputStream in = getClass().getResourceAsStream("History.txt");
        try {
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            StringBuffer result = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                if (line.length() == 0) {
                    // new paragraph
                    result.append('\n');
                } else {
                    result.append(" ");
                }
                line = reader.readLine();
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            // can not happen.
            return "Unsupported Encoding: " + e.getMessage();
        } finally {
            in.close();
        }
    }
}
